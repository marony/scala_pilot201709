package com.binbo_kodakusan.tx

import com.binbo_kodakusan.common.{AroundFilter, ResultCode, Success}
import com.binbo_kodakusan.tx.parameter.{Messages, OrderParam, TxState, TxResult}
import com.binbo_kodakusan.tx.request.OrderRequest

trait MixInOrderAroundFilter extends AroundFilter[TxState, Unit] {
  val checkUser = new CheckUser
  val checkVegetable = new CheckVegetable

  // TODO: モナドにする
  override def before(state: TxState): (Unit, TxState) = {
    println("MixInOrderAroundFilter.before")
    val r1 = checkUser.process(state)
    println(s"r1 = $r1")
    r1._2.result match {
      case None => {
        // 成功
        val r2 = checkVegetable.process(r1._2)
        println(s"r2 = $r2")
        r2._2.result match {
          case None =>
            // 成功
            ((), r2._2)
          case Some(code) =>
            println(s"r2' = $r2")
            ((), r2._2)
        }
      }
      case Some(code) => {
        println(s"r1' = $r1")
        ((), r1._2)
      }
    }
  }
  override def after(state: TxState): (Unit, TxState) = {
    println("MixInOrderAroundFilter.after")
    ((), state)
  }
}
