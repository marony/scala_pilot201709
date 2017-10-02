package com.binbo_kodakusan.api.request

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

case class OrderRequest(vegetableId: Int, orderPrice: BigDecimal, orderQty: String) {
}
