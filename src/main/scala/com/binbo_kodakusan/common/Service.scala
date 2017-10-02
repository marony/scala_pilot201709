package com.binbo_kodakusan.common

trait Service[S, R] {
  self: AroundFilter[S, R] =>

  def process(state: S): (R, S) = {
    println(s"Service.process state = $state")
    val r1 = before(state)
    println(s"Service.process r1 = $r1")
    val r2 = run(r1._2)
    println(s"Service.process r2 = $r2")
    val r3 = after(r2._2)
    println(s"Service.process r3 = $r3")
    r3
  }

  // ロジック本体
  protected def run(state: S): (R, S)
}
