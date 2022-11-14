package me.kvdpxne.covilo.domain.services

import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.models.AccountAlreadyExistsException
import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.repositories.RefreshTokenRepository
import me.kvdpxne.covilo.domain.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service("application-user-service")
class UserLifecycle(
  private val passwordEncoder: PasswordEncoder,
  private val userRepository: UserRepository,
  private val refreshTokenRepository: RefreshTokenRepository,
  private val refreshTokenLifecycle: RefreshTokenLifecycle
) : AccountManagementService {

  override fun isExists(email: String): Boolean =
    null != userRepository.findByEmail(email)

  @Throws(AccountAlreadyExistsException::class)
  override fun create(account: Account): User {
    val email = account.email
    if (isExists(email)) {
      throw AccountAlreadyExistsException(email)
    }
    val user = (account as User).copy(
      password = passwordEncoder.encode(account.password)
    )
    userRepository.insert(user)
    refreshTokenLifecycle.create(user)
    return user
  }

  override fun find(email: String): Account {
    return userRepository.findByEmail(email)!!
  }

  fun update() {

  }

  fun delete(account: Account): Boolean {
    TODO("Not yet implemented")
  }

  fun count(): UInt = userRepository.count().toUInt()
}