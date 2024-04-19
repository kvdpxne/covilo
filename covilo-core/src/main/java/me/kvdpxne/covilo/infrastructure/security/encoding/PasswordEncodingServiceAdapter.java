package me.kvdpxne.covilo.infrastructure.security.encoding;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.service.PasswordEncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Adapter class implementing the {@link PasswordEncodingService} interface
 * by using Spring Security's {@link PasswordEncoder}.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class PasswordEncodingServiceAdapter
  implements PasswordEncodingService {

  /**
   * The {@link PasswordEncoder} provided by Spring Security.
   */
  private final PasswordEncoder passwordEncoder;

  @Override
  public String encode(
    final String rawPassword
  ) {
    return this.passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(
    final String rawPassword,
    final String encodedPassword
  ) {
    return this.passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
