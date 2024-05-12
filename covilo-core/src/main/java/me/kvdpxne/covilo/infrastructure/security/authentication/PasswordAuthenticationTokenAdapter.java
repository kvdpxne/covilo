package me.kvdpxne.covilo.infrastructure.security.authentication;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.service.PasswordAuthenticator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class PasswordAuthenticationTokenAdapter
  implements PasswordAuthenticator {

  private final AuthenticationManager authenticationManager;

  @Override
  public void authenticate(
    final String name,
    final String password
  ) {
    this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        name,
        password
      )
    );
  }
}
