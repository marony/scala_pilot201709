package com.binbo_kodakusan.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._

import scalikejdbc._
import org.joda.time._

case class User(id: UserId, name: UserName)

object User extends SQLSyntaxSupport[User] {
  override val tableName = "mt_users"
  def apply(u: ResultName[User], rs: WrappedResultSet) = new User(
    UserId(rs.string(u.id)), UserName(rs.string(u.name)))
}

// TODO: エンティティとドメインが同じクラスでいいか考える
// TODO: プロパティがネストしたJSONになっちゃうの直す
