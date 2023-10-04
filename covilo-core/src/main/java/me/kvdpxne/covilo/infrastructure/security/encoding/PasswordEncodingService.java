package me.kvdpxne.covilo.infrastructure.security.encoding;

import me.kvdpxne.covilo.application.IPasswordEncodingUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class PasswordEncodingService
  implements IPasswordEncodingUseCase {

  private final PasswordEncoder passwordEncoder;

  public PasswordEncodingService(final PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(final String password) {
    // Uses the defined PasswordEncoder from the PasswordEncoderConfiguration
    // to encode the given raw currentPassword.
    return this.passwordEncoder.encode(password);
  }
}
