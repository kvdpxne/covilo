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
    TODO("Not yet implemented")
  }

  override fun getUsername(): String {
    TODO("Not yet implemented")
  }

  override fun isAccountNonExpired(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isAccountNonLocked(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isCredentialsNonExpired(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isEnabled(): Boolean {
    TODO("Not yet implemented")
  }
}