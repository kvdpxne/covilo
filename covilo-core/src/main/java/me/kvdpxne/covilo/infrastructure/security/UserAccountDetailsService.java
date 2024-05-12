package me.kvdpxne.covilo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserAccountDetailsService
  implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(
    final String username
  ) throws UsernameNotFoundException {
    return this.userRepository.findUserByEmail(username)
      .map(UserAccountDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException(
        STR."Not user found for \"\{username}\" username."
      ));
  }
}
