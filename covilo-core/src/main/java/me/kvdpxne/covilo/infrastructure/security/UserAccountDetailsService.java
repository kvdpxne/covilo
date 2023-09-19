package me.kvdpxne.covilo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserAccountDetailsService implements UserDetailsService {

  private final IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final User user = this.userRepository.findUserByEmailOrNull(username);
    if (null == user) {
      throw new UsernameNotFoundException(String.format(
        "Not user found for \"%s\" username.",
        username
      ));
    }
    return new UserAccountDetails(user);
  }
}
