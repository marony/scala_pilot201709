package com.binbo_kodakusan.api.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

import com.binbo_kodakusan.common.ResultCode

case class Response(val code: ResultCode) {
}
