package com.binbo_kodakusan.persistence

import com.binbo_kodakusan.domain.{Vegetable, VegetableId}

// 使われる側インターフェース
trait VegetableRepository extends Repository[VegetableId, Vegetable] {
  def get(id: VegetableId): Option[Vegetable]
  def insert(entity: Vegetable): Unit
}

// 使う側インターフェース
trait UsesVegetableRepository {
  val vegetableRepository: VegetableRepository
}
