package com.binbo_kodakusan.tx.parameter

import com.binbo_kodakusan.common.ResultCode
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

case class TxResult(
  val code: ResultCode,
  val messagesOp: Option[Messages] = None)
