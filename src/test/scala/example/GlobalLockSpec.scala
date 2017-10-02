package example

import scala.concurrent.ExecutionContext.Implicits.global
import com.binbo_kodakusan.common.{UserLockScope, UserLock, Using}
import org.scalatest._

class GlobalLockSpec extends FlatSpec with Matchers {
  "The UserLock" should "lock user" in {
    var r1 = 0
    var r2 = 0
    // シングルスレッド
    (0 to 100).foreach { i =>
      r1 = r1 + i
    }
    // マルチスレッド
    (0 to 100).par.foreach { i =>
      Using(new UserLockScope("localhost:2181", 3000, "12345678")) {
        lock => r2 = r2 + i
      }
    }
    r1 should be (r2)
  }
}
