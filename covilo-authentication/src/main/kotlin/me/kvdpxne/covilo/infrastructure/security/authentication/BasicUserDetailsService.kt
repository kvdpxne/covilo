package me.kvdpxne.covilo.infrastructure.security.authentication

import me.kvdpxne.covilo.domain.repositories.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class BasicUserDetailsService(
  private val accountRepository: AccountRepository
) : UserDetailsService {

  override fun loadUserByUsername(email: String): UserDetails =
    accountRepository.findByEmail(email)?.let { BasicUserDetails(it) }
      ?: throw UsernameNotFoundException("User: $email, not found.")
}