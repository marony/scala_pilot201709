package com.binbo_kodakusan.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import scalikejdbc._

case class Vegetable(id: VegetableId, name: VegetableName)

object Vegetable extends SQLSyntaxSupport[Vegetable] {
  override val tableName = "mt_vegetables"
  def apply(u: ResultName[Vegetable], rs: WrappedResultSet) = new Vegetable(
    VegetableId(rs.string(u.id)), VegetableName(rs.string(u.name)))
}

// TODO: エンティティとドメインが同じクラスでいいか考える
// TODO: プロパティがネストしたJSONになっちゃうの直す
