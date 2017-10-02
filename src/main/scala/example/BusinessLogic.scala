package example

// ドワンゴのコメント見本
/**
  * 『PofEAA』の「Unit of Work」パターンの実装
  *
  * トランザクションとはストレージに対するまとまった処理である
  * トランザクションオブジェクトとはトランザクションを表現するオブジェクトで、
  * 具体的にはデータベースライブラリのセッションオブジェクトなどが該当する
  *
  * @tparam Resource トランザクションオブジェクトの型
  * @tparam A トランザクションを実行して得られる値の型
  */

/**
  * トランザクションの内部で実行される個々の処理の実装
  * このメソッドを実装することでTaskが作られる
  *
  * @param resource トランザクションオブジェクト
  * @param ec ExecutionContext
  * @return トランザクションの内部で実行される個々の処理で得られる値
  */

// リポジトリ
trait Repository[I, E] {
  def get(id: I): E
  def insert(entity: E): Unit
}

// ロジック前後処理
trait AroundFilter[P, R] {
  protected def before(param: P): (R, P)
  protected def after(param: P): (R, P)
}

// ロジック基本
trait Service[P, R] {
  self: AroundFilter[P, R] =>

  def process(param: P): (R, P) = {
    val r1 = before(param)
    val r2 = run(r1._2)
    val r3 = after(r2._2)
    r3
  }

  // ロジック本体
  protected def run(param: P): (R, P)
}

// ユーザID
case class UserId(id: String)
// ユーザエンティティ
case class User(id: UserId, name: String)
// 注文ID
case class OrderId(id: String)
// 注文
case class Order(id: OrderId)

// ユーザリポジトリ
// 使われる側インターフェース
trait UserRepository extends Repository[UserId, User] {
  def get(id: UserId): User
  def insert(entity: User): Unit
}

// 使う側インターフェース
trait UsesUserRepository {
  val userRepository: UserRepository
}

// 使われる側実装
class UserRepositoryImpl extends UserRepository {
  override def get(id: UserId): User = {
    println("UserRepositoryImpl.get")
    User(id, "name")
  }
  override def insert(entity: User): Unit = {
    println("UserRepositoryImpl.insert")
  }
}

// 使う側実装
trait MixInUserRepository /* extends UsesUserRepository */ {
  val userRepository = new UserRepositoryImpl
}

// 注文リポジトリ
// 使われる側インターフェース
trait OrderRepository extends Repository[OrderId, Order] {
  def get(id: OrderId): Order
  def insert(entity: Order): Unit
}

// 使う側インターフェース
trait UsesOrderRepository {
  val orderRepository: OrderRepository
}

// 使われる側実装
class OrderRepositoryImpl extends OrderRepository {
  override def get(id: OrderId): Order = {
    println("OrderRepositoryImpl.get")
    Order(id)
  }
  override def insert(entity: Order): Unit = {
    println("OrderRepositoryImpl.insert")
  }
}

// 使う側実装
trait MixInOrderRepository /* extends UsesOrderRepository */ {
  val orderRepository = new OrderRepositoryImpl
}

// 発注サービスインターフェース
trait OrderService extends Service[OrderParam, OrderResult]
  with AroundFilter[OrderParam, OrderResult]
  with UsesOrderRepository
  with UsesUserRepository {
}

// 発注パラメータ
case class OrderParam()
// 発注結果
case class OrderResult()

// 発注前後処理
trait MixInOrderAroundFilter extends AroundFilter[OrderParam, OrderResult] {
  override def before(param: OrderParam): (OrderResult, OrderParam) = {
    println("OrderAroundFilter.before")
    (OrderResult(), param)
  }
  override def after(param: OrderParam): (OrderResult, OrderParam) = {
    println("OrderAroundFilter.after")
    (OrderResult(), param)
  }
}

// 発注サービス実装
// TODO: サービス名がよくない
class OrderServiceImpl extends OrderService
  with MixInOrderAroundFilter
  with MixInOrderRepository
  with MixInUserRepository {

  protected override def run(param: OrderParam): (OrderResult, OrderParam) = {
    val user = userRepository.get(UserId("1"))
    val order = orderRepository.get(OrderId("A"))
    orderRepository.insert(order)
    userRepository.insert(user)
    (OrderResult(), param)
  }
}

// TODO: セッション・トランザクション
// TODO:
object BusinessLogicApp extends App {
  val orderService = new OrderServiceImpl
  val r = orderService.process(new OrderParam)
  println(r)
}
