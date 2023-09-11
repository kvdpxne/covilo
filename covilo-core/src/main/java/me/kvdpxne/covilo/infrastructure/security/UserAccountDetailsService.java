package me.kvdpxne.covilo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.infrastructure.jpa.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserAccountDetailsService implements UserDetailsService {

  private final UserDao userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
      .map(UserAccountDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException(String.format("Not user found for \"%s\" username.", username)));
  }
}
