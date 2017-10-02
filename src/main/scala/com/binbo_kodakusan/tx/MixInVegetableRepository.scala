package com.binbo_kodakusan.tx

import com.binbo_kodakusan.domain.{Vegetable, VegetableId, VegetableName}
import com.binbo_kodakusan.persistence.VegetableRepository
import scalikejdbc._
import org.joda.time._

// 使われる側実装
class VegetableRepositoryImpl extends VegetableRepository {
  implicit val session = AutoSession

  override def get(id: VegetableId): Option[Vegetable] = {
    println("VegetableRepositoryImpl.get")
    val v = Vegetable.syntax("v")
    withSQL {
      select.from(Vegetable as v).where.eq(v.id, id.vegetableId)
    }.map(rs => Vegetable(v.resultName, rs)).single().apply()
  }
  override def insert(entity: Vegetable): Unit = {
    println("VegetableRepositoryImpl.insert")
  }
}

// 使う側実装
trait MixInVegetableRepository /* extends VegetableVegetableRepository */ {
  val vegetableRepository = new VegetableRepositoryImpl
}

// TODO: VegetableRepositoryImplはモジュール構成考える
