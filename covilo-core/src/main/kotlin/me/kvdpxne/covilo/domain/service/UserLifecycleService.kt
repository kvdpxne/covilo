package me.kvdpxne.covilo.domain.service

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.persistence.UserRepository
import me.kvdpxne.covilo.util.slf4j.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserLifecycleService @Autowired constructor(
  private val repository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) {

  companion object {
    private val logger = logger(UserLifecycleService::class)
  }


  fun createUser(user: User): User {
    val newUser = user.copy(
      password = passwordEncoder.encode(user.password)
    )
    repository.insert(newUser)
    logger.info(newUser.toString())
    return newUser
  }
}