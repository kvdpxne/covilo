package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyPrincipal(
  val user: User
) : UserDetails {

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    TODO("Not yet implemented")
  }

  override fun getPassword(): String {
    return user.password
  }

  override fun getUsername(): String {
    return user.email
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