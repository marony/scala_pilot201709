package com.binbo_kodakusan.tx

import com.binbo_kodakusan.common.{Check, NoVegetable, Success}
import com.binbo_kodakusan.tx.parameter.{TxState, TxResult}

/**
  * ユーザの存在チェックをする
  */
class CheckVegetable
  extends Check[TxState, Unit]
    with MixInVegetableRepository {
  protected override def run(state: TxState): (Unit, TxState) = {
    println("CheckVegetable.run")
    vegetableRepository.get(state.param.vegetableId) match {
      case Some(vegetable) =>
        ((), TxState(state.param, None, state.userOp, Some(vegetable)))
      case None =>
        ((), TxState(state.param, Some(TxResult(NoVegetable())), state.userOp, state.vegetableOp))
    }
  }
}
