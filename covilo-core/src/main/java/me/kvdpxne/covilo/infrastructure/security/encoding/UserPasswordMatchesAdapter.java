package me.kvdpxne.covilo.infrastructure.security.encoding;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.port.out.UserPasswordMatchesPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class UserPasswordMatchesAdapter
  implements UserPasswordMatchesPort {

  private final PasswordEncoder passwordEncoder;

  @Override
  public boolean matches(
    final String rawPassword,
    final String encodedPassword
  ) {
    return this.passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
