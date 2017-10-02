package com.binbo_kodakusan.tx.parameter

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

import com.binbo_kodakusan.domain.{IdempotencyId, UserId, VegetableId}

case class OrderParam(
  idempotencyId: IdempotencyId,
  userId: UserId,
  vegetableId: VegetableId,
  orderPrice: BigDecimal,
  orderQty: BigDecimal)
