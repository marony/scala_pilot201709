package com.binbo_kodakusan.tx

import com.binbo_kodakusan.domain.{User, UserId, UserName}
import com.binbo_kodakusan.persistence.UserRepository
import scalikejdbc._
import org.joda.time._

// 使われる側実装
class UserRepositoryImpl extends UserRepository {
  implicit val session = AutoSession

  override def get(id: UserId): Option[User] = {
    println("UserRepositoryImpl.get")
    val u = User.syntax("u")
    withSQL {
      select.from(User as u).where.eq(u.id, id.userId)
    }.map(rs => User(u.resultName, rs)).single().apply()
  }
  override def insert(entity: User): Unit = {
    println("UserRepositoryImpl.insert")
  }
}

// 使う側実装
trait MixInUserRepository /* extends UsesUserRepository */ {
  val userRepository = new UserRepositoryImpl
}

// TODO: UserRepositoryImplはモジュール構成考える
