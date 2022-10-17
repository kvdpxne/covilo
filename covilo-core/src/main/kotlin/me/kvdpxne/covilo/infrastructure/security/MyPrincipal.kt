package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

private typealias Authorities = MutableCollection<out GrantedAuthority>

class MyPrincipal constructor(
  val user: User
) : UserDetails {

  companion object {
    private const val serialVersionUID: Long = 2031976890L
  }

  override fun getUsername(): String = user.email

  override fun getPassword(): String = user.password

  override fun getAuthorities(): Authorities = user.roles
    .map { name: String -> SimpleGrantedAuthority("ROLE_$name") }
    .toMutableSet()

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true
}