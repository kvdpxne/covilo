package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

private typealias Authorities = MutableCollection<out GrantedAuthority>

class MyPrincipal(val user: User) : UserDetails {

  companion object {
    private const val serialVersionUID: Long = 2031976890L
  }

  override fun getUsername(): String {
    return user.email
  }

  override fun getPassword(): String {
    return user.password
  }

  override fun getAuthorities(): Authorities {
    return user.roles
      .map { name: String -> SimpleGrantedAuthority("ROLE_$name") }
      .toMutableSet()
  }

  override fun isAccountNonExpired(): Boolean {
    return true
  }

  override fun isAccountNonLocked(): Boolean {
    return true
  }

  override fun isCredentialsNonExpired(): Boolean {
    return true
  }

  override fun isEnabled(): Boolean {
    return true
  }
}