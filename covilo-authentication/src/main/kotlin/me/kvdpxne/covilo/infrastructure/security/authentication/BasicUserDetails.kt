package me.kvdpxne.covilo.infrastructure.security.authentication

import me.kvdpxne.covilo.domain.models.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

private typealias Authorities = MutableCollection<out GrantedAuthority>

class BasicUserDetails(val account: Account) : UserDetails {

  companion object {
    private const val serialVersionUID = 2031976890L
  }

  override fun getAuthorities(): Authorities =
    account.roles
      .map { SimpleGrantedAuthority("ROLE_$it") }
      .toMutableSet()

  override fun getPassword(): String = account.password

  override fun getUsername(): String = account.email

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = account.enabled
}