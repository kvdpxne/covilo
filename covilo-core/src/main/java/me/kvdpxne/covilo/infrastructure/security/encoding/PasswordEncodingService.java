package me.kvdpxne.covilo.infrastructure.security.encoding;

import me.kvdpxne.covilo.application.PasswordEncodingUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class PasswordEncodingService
  implements PasswordEncodingUseCase {

  private final PasswordEncoder passwordEncoder;

  public PasswordEncodingService(final PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(final String password) {
    // Uses the defined PasswordEncoder from the PasswordEncoderConfiguration
    // to encode the given raw password.
    return this.passwordEncoder.encode(password);
  }
}
