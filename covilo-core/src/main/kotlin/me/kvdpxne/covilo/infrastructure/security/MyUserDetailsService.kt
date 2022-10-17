package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class MyUserDetailsService @Autowired constructor(
  private val repository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    repository.findByEmail(username)?.let { user: User -> MyPrincipal(user) }
      ?: throw UsernameNotFoundException("User: $username, not found.")
}