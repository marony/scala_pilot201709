package com.binbo_kodakusan.tx

import com.binbo_kodakusan.common.{Check, NoUser, Success}
import com.binbo_kodakusan.tx.parameter.{TxState, TxResult}

/**
  * ユーザの存在チェックをする
  */
class CheckUser
  extends Check[TxState, Unit]
  with MixInUserRepository {
  protected override def run(state: TxState): (Unit, TxState) = {
    println("CheckUser.run")
    val user = userRepository.get(state.param.userId)
    println(s"user = $user")
    user match {
      case Some(user) =>
        ((), TxState(state.param, None, Some(user), state.vegetableOp))
      case None =>
        ((), TxState(state.param, Some(TxResult(NoUser())), state.userOp, state.vegetableOp))
    }
  }
}
