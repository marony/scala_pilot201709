package com.binbo_kodakusan.persistence

trait Repository[I, E] {
  def get(id: I): Option[E]
  def insert(entity: E): Unit
}
