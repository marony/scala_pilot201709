package com.binbo_kodakusan.persistence

import com.binbo_kodakusan.domain.{User, UserId}

// 使われる側インターフェース
trait UserRepository extends Repository[UserId, User] {
  def get(id: UserId): Option[User]
  def insert(entity: User): Unit
}

// 使う側インターフェース
trait UsesUserRepository {
  val userRepository: UserRepository
}
