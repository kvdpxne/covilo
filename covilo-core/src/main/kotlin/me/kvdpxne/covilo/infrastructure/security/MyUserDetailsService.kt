package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class MyUserDetailsService(
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    return userRepository.findByEmail(username)?.let { MyPrincipal(it) }
      ?: throw UsernameNotFoundException("User: $username, not found.")
  }
}