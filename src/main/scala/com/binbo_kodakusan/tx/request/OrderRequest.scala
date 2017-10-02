package com.binbo_kodakusan.tx.request

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

import com.binbo_kodakusan.domain._

// userIdはURIから取得
case class OrderRequest(
  idempotencyId: String,
  vegetableId: String,
  orderPrice: String,
  orderQty: String)
