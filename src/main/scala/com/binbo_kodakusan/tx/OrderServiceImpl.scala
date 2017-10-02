package com.binbo_kodakusan.tx

import com.binbo_kodakusan.common.{ResultCode, Success}
import com.binbo_kodakusan.tx.parameter.{OrderParam, TxState, TxResult}
import com.binbo_kodakusan.tx.request.OrderRequest

trait OrderServiceImpl extends OrderService {
  protected override def run(state: TxState): (Unit, TxState) = {
    println("OrderServiceImpl.run")
    (state.userOp, state.vegetableOp) match {
      case (Some(user), Some(vegetable)) =>
        println(s"${user.name.name}が${vegetable.name.name}を買いました")
      case _ =>
        println("失敗: $state")
    }
    ((), state)
  }
}
