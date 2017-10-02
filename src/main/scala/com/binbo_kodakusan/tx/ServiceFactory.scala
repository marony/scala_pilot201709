package com.binbo_kodakusan

import com.binbo_kodakusan._
import com.binbo_kodakusan.tx.{MixInOrderAroundFilter, OrderService, OrderServiceImpl}

object ServiceFactory {
  def orderService: tx.OrderService = new OrderService with OrderServiceImpl with MixInOrderAroundFilter
}
