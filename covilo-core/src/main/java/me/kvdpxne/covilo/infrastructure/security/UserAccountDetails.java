package me.kvdpxne.covilo.infrastructure.security;

import java.util.Collection;
import java.util.List;
import me.kvdpxne.covilo.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserAccountDetails(User user) implements UserDetails  {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    var role = this.user.role();
//
//    var authorities = role
//      .getPermissions()
//      .stream()
//      .map(permission -> new SimpleGrantedAuthority(permission.name()))
//      .collect(Collectors.toList());
//
//    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
//    return authorities;
    return List.of();
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getName();
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
