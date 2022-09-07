package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.User
import me.kvdpxne.covilo.domain.model.Users
import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserDao : UserRepository {
  override fun findByIdentifier(identifier: UUID): User? {
    TODO("Not yet implemented")
  }

  override fun findByName(name: String): User? {
    TODO("Not yet implemented")
  }

  override fun findAll(): Users {
    TODO("Not yet implemented")
  }

  override fun insert(user: User) {
    TODO("Not yet implemented")
  }

  override fun update(user: User) {
    TODO("Not yet implemented")
  }

  override fun delete(identifier: UUID) {
    TODO("Not yet implemented")
  }

  override fun truncate() {
    TODO("Not yet implemented")
  }

  override fun count(): Long {
    TODO("Not yet implemented")
  }
}