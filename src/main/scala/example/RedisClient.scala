package example
import com.redis._

object RedisClientApp extends App {
  val r = new RedisClient("localhost", 6379)
  r.set("key", "some value")
  println(r.get("key"))
}
