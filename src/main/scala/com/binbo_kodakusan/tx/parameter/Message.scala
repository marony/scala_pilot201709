package com.binbo_kodakusan.tx.parameter

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

case class Message(message: String)
