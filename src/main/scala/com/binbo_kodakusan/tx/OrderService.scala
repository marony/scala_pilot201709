package com.binbo_kodakusan.tx

import com.binbo_kodakusan._
import com.binbo_kodakusan.common.{AroundFilter, ResultCode, Service}
import com.binbo_kodakusan.tx.parameter.{OrderParam, TxState, TxResult}
import com.binbo_kodakusan.tx.request.OrderRequest

trait OrderService extends Service[TxState, Unit]
  with AroundFilter[TxState, Unit] {
}
