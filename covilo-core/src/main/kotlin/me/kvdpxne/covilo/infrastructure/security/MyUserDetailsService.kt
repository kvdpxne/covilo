package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class MyUserDetailsService @Autowired(required = true) constructor(
  private val repository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(name: String): UserDetails {
    return repository.findByEmail(name)?.let {
      MyPrincipal(it)
    } ?: throw UsernameNotFoundException(name)
  }
}