package com.binbo_kodakusan.tx.response

import com.binbo_kodakusan.common.ResultCode
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

case class Response(
  val code: ResultCode)
