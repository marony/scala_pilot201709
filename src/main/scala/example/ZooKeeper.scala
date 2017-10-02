package example

import org.apache.zookeeper._
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.ZooDefs.Ids._
import org.apache.zookeeper.ZooDefs.Perms._
import java.util

import java.time.LocalDateTime

object ZooKeeperApp extends App with Watcher {
  val zk = new ZooKeeper("127.0.0.1:2181", 3000, this)

  val path = "/test"
  val stat = zk.exists(path, true)
  if (stat == null) {
    val acls = new util.ArrayList[ACL]
    acls.add(new ACL(ALL, ANYONE_ID_UNSAFE))
    zk.create(path, LocalDateTime.now().toString.getBytes, acls, CreateMode.PERSISTENT)
  } else {
    val stat2 = zk.setData(path, LocalDateTime.now().toString.getBytes, stat.getVersion)
  }
  val bytes = zk.getData(path, false, null)
  if (bytes != null) {
    val s = new String(bytes)
    println(s)
  }

  def process(event: WatchedEvent): Unit = {
    println(event)
  }
}
