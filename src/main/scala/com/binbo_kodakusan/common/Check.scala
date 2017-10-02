package com.binbo_kodakusan.common

trait Check[S, R] {
  def process(state: S): (R, S) = run(state)
  // ロジック本体
  protected def run(state: S): (R, S)
}
