package com.binbo_kodakusan.common

import org.apache.zookeeper._
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.ZooDefs.Ids._
import org.apache.zookeeper.ZooDefs.Perms._
import org.apache.zookeeper.KeeperException.NodeExistsException

import scala.collection.JavaConverters._
import com.typesafe.scalalogging.slf4j.{LazyLogging, Logger, StrictLogging}
import org.slf4j.LoggerFactory

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * ZooKeeperを用いたシステムレベルのロック
  *
  * ZooKeeper上に
  *
  * /__lock__/users/$ユーザID/seq-0000056876
  * /__lock__/users/$ユーザID/seq-0000056877
  *
  * のようなロック管理用のノードを作成する
  */
trait GlobalLock
  extends Watcher
  with LazyLogging {

  val zk: ZooKeeper

  /** ロックに使用するノード */
  val LockBasePath = "/__lock__"
  /** ロックの種類ごとのサブノード */
  val LockTypePath: String
  /** ロックに使用するノード名 */
  val LockName = "seq-"
  // TODO: 全体ロックの実装
  val All = "*"

  private def getLockTypePath = LockBasePath + LockTypePath
  private def getLockPath(id: String) = getLockTypePath + "/" + id
  
  case class LockWatcher(id: String, name: String, promise: Promise[Int])
    extends Watcher {

    protected def process(event: WatchedEvent): Unit = {
      logger.debug(s"LockWatcher.process: $event")
      promise.success(0)
    }
  }

  protected def initialize: Unit = {
    val path = getLockTypePath
    val r = ZkUtil.createPath(zk, path, CreateMode.PERSISTENT)
  }

  // TODO: 美しくない
  def shutdown: Unit = {
    if (zk != null) {
      zk.close()
    }
  }

  /**
    * ロック確保
    *
    * @param id
    * @return
    */
  def lock(id: String): Future[String] = {
    Future {
      var flag = false
      var name = ""
      try {
        // idのノード作成
        {
          val path = getLockPath(id)
          val r = ZkUtil.createPath(zk, path, CreateMode.PERSISTENT)
        }
        {
          // ロック用ノード作成
          val data = "".getBytes()
          val createdNode  = {
            val path = getLockPath(id) + "/" + LockName
            zk.create(path, data, ZkUtil.acls, CreateMode.EPHEMERAL_SEQUENTIAL)
          }
          name = createdNode
          while (!lockLoop(id, createdNode)) {
            // ロック確保するまでループ
          }
        }
      }
      catch {
        case e: KeeperException =>
          logger.error(s"error: ", e)
      }
      name
    }
  }

  /**
    *
    * @param id
    * @param createdNode
    * @return
    */
  private def lockLoop(id: String, createdNode: String): Boolean = {
    // ロック用ノード列挙(自ノードが先頭ならばロック確保)
    val path = getLockPath(id)
    val children = ZkUtil.getChildren(zk, path)
    val first = children.head
    val createdName = createdNode.substring(createdNode.length - first.length, createdNode.length)
    if (first == createdName) {
      // ロックを確保できた
      logger.debug(s"locked: $createdName")
      true
    } else {
      // 自分の1つ前のノードをWatchして解放を待つ
      val prev = children(children.indexOf(createdName) - 1)
      val promise = Promise[Int]
      val lock = new LockWatcher(id, prev, promise)
      val path = getLockPath(id) + "/" + prev
      logger.debug(s"watch: $path, me = $createdName, first = $first")
      val stat = zk.exists(path, lock)
      if (stat != null) {
        // 誰かに確保されているのでWatcherが呼ばれるまで待つ
        val f = promise.future
        Await.ready(f, Duration.Inf)
        // 先頭ノードに変化があった(通常は削除)のでもう一度チェック
      }
      // 存在しない場合は、既に削除されたのでもう一度チェック
      false
    }
  }

  /**
    * ロック解放
    *
    * @param name
    * @return
    */
  def unlock(name: String): Future[Boolean] = {
    Future {
      val stat = zk.exists(name, false)
      if (stat != null) {
        logger.debug(s"unlock: $name")
        zk.delete(name, stat.getVersion)
        true
      } else {
        false
      }
    }
  }

  protected def process(event: WatchedEvent): Unit = {
    logger.debug(s"GlobalLock.process: $event")
  }
}

/**
  * ユーザ単位のロック
  *
  * @param hostPort
  * @param sessionTimeout
  */
class UserLock(val hostPort: String, val sessionTimeout: Int)
  extends GlobalLock {
  override val zk = new ZooKeeper(hostPort, sessionTimeout, this)
  override val LockTypePath = "/users"

  initialize
}

/**
  * 野菜単位のロック
  *
  * @param hostPort
  * @param sessionTimeout
  */
class VegetableLock(val hostPort: String, val sessionTimeout: Int)
  extends GlobalLock {
  override val zk = new ZooKeeper(hostPort, sessionTimeout, this)
  override val LockTypePath = "/vegetables"

  initialize
}

// FIXME: ジェネリクスで実現したい
//class LockScope[A <: GlobalLock](val hostPort: String, val sessionTimeout: Int, val id: String) {
//  private val lock = new A(hostPort, sessionTimeout)
//  Await.ready(lock.lock(id), Duration.Inf)
//
//  def close(): Unit = lock.shutdown
//}
//
//object LockScope {
//  implicit val lockCloser: Closer[LockScope[GlobalLock]] = Closer(_.close())
//}

class UserLockScope(val hostPort: String, val sessionTimeout: Int, val id: String) {
  private val lock = new UserLock(hostPort, sessionTimeout)
  private val f = lock.lock(id)
  private val name = Await.result(f, Duration.Inf)

  def close(): Unit = {
    Await.ready(lock.unlock(name), Duration.Inf)
    lock.shutdown
  }
}

object UserLockScope {
  implicit val lockCloser: Closer[UserLockScope] = Closer(_.close())
}

class VegetableLockScope(val hostPort: String, val sessionTimeout: Int, val id: String) {
  private val lock = new VegetableLock(hostPort, sessionTimeout)
  Await.ready(lock.lock(id), Duration.Inf)

  def close(): Unit = lock.shutdown
}

object VegetableLockScope {
  implicit val lockCloser: Closer[VegetableLockScope] = Closer(_.close())
}
