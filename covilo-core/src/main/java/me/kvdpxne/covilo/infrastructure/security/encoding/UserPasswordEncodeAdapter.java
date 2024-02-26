package me.kvdpxne.covilo.infrastructure.security.encoding;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.port.out.UserPasswordEncodePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class UserPasswordEncodeAdapter
  implements UserPasswordEncodePort {

  private final PasswordEncoder passwordEncoder;

  @Override
  public String encode(
    final String rawPassword
  ) {
    return this.passwordEncoder.encode(rawPassword);
  }
}
