package com.binbo_kodakusan.common

import com.binbo_kodakusan.tx.parameter.TxState
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

sealed trait ResultCode

// TODO: ジェネリクスにしたいけど、JsonEncoderが定義されなくてエラー
//case class Success[S](s: S) extends ResultCode
case class Success(s: TxState) extends ResultCode
case class NoUser() extends ResultCode
case class NoVegetable() extends ResultCode

// TODO: 使う側のコードか共通に移動する
//object ResultCodeCodec {
//  implicit val decodeSuccess: Decoder[Success[TxState]] =
//    Decoder.forProduct1("s")(Success.apply[TxState])
//
//  implicit val encodeSuccess: Encoder[Success[TxState]] =
//    Encoder.forProduct1("s")(u => (u.s))
//}
