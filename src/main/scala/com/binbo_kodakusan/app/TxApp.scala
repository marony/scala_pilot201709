package com.binbo_kodakusan.app

import com.binbo_kodakusan._
import com.binbo_kodakusan.common.Success
import com.binbo_kodakusan.tx.request.OrderRequest
import com.binbo_kodakusan.tx.response.Response
import com.binbo_kodakusan.tx.parameter.{OrderParam, TxState}
import com.binbo_kodakusan.domain._
import com.binbo_kodakusan.tx.{MixInOrderAroundFilter, OrderService, OrderServiceImpl}
import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._

/***
  * トランザクション(tx)サーバ
  */
object TxApp extends App {
  // /orders/$userId/
  val route = "orders" :: string("userId")

  // resources/application.confからScalikeJDBCをセットアップ
  scalikejdbc.config.DBs.setupAll()

  /**
    * 野菜の発注
    *
    * http://localhost:8081/orders/$userId/order
    * body:
{
    "idempotencyId": "1",
    "vegetableId": "A001",
    "orderPrice": "100.0",
    "orderQty": "1000.0"
}
    */
  val order =
    post(route :: "order" :: jsonBody[OrderRequest]) { (userId: String, request: OrderRequest) =>
      println(request)
      // リクエストをパラメータに変換
      val user = UserId(userId)
      val param = OrderParam(
        IdempotencyId(request.idempotencyId), UserId(userId), VegetableId(request.vegetableId),
        BigDecimal(request.orderPrice), BigDecimal(request.orderQty))
      // サービス作成
      val service = ServiceFactory.orderService
      // サービス呼び出し
      val result = service.process(TxState(param))
      println(s"result = $result")
      Ok(result._2)
    }

  /**
    *
    */
  val replace =
    post(route :: "replace" :: string("idempotencyId")) { (userId: String, idempotencyId: String) =>
      println(userId, idempotencyId)
      Ok(1)
    }

  Await.ready(Http.server.serve(":8081", (
    order :+: replace
    ).toService))
}
