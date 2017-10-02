package com.binbo_kodakusan.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

case class Price(price: BigDecimal)
