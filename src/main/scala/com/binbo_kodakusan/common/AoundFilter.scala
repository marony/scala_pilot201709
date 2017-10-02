package com.binbo_kodakusan.common

trait AroundFilter[S, R] {
  protected def before(state: S): (R, S)
  protected def after(state: S): (R, S)
}
