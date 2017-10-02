package com.binbo_kodakusan.common

import org.apache.zookeeper._
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.ZooDefs.Ids._
import org.apache.zookeeper.ZooDefs.Perms._
import org.apache.zookeeper.KeeperException.NodeExistsException

import scala.collection.JavaConverters._
import com.typesafe.scalalogging.slf4j.{LazyLogging, StrictLogging}

import scala.collection.mutable

object ZkUtil extends LazyLogging {
  val acls = List[ACL](new ACL(ALL, ANYONE_ID_UNSAFE)).asJava

  /***
    * "/a/b/c"
    * SEQUENCEはダメ
    *
    * @param zk
    * @param path
    * @param mode
    * @return
    */
  def createPath(zk: ZooKeeper, path: String, mode: CreateMode): String = {
    val names = path.split('/')
    var nowPath = ""
    var r: String = null
    for (n <- names) {
      if (n.length > 0) {
        nowPath += "/" + n
        if (zk.exists(nowPath, false) == null) {
          val data = "".getBytes()
          try {
            r = zk.create(nowPath, data, acls, mode)
          }
          catch {
            case e: NodeExistsException =>
            // 何もしない
            case e: Exception => {
              println(e)
              for (s <- e.getStackTrace) {
                println(s)
              }
            }
          }
        }
      }
    }
    r
  }

  def getChildren(zk: ZooKeeper, path: String): mutable.Buffer[String] = {
    zk.getChildren(path, false).asScala.sorted
  }
}
