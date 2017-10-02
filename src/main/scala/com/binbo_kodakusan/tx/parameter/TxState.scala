package com.binbo_kodakusan.tx.parameter

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

import com.binbo_kodakusan.domain._
import com.binbo_kodakusan.tx.request.OrderRequest

case class TxState(
  val param: OrderParam,
  val result: Option[TxResult] = None,
  val userOp: Option[User] = None,
  val vegetableOp: Option[Vegetable] = None)
