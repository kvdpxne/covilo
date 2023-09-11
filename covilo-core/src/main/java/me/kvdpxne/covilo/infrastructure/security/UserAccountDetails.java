package me.kvdpxne.covilo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class UserAccountDetails implements UserDetails {

  private final UserEntity user;

  public UserEntity getUser() {
    return this.user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var role = this.user.getRole();

    var authorities = role
      .getPermissions()
      .stream()
      .map(permission -> new SimpleGrantedAuthority(permission.name()))
      .collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
