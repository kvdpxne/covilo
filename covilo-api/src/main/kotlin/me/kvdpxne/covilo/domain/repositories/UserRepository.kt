package me.kvdpxne.covilo.domain.repositories

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.models.Users
import java.util.UUID

/**
 * @see User
 */
interface UserRepository : AccountRepository {

  fun findByIdentifier(identifier: UUID): User?

  fun findAll(): Users

  fun insert(user: User)

  fun update(user: User)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}