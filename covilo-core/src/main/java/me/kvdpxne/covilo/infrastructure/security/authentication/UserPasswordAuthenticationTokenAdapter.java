package me.kvdpxne.covilo.infrastructure.security.authentication;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.port.out.UserPasswordAuthenticationTokenPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class UserPasswordAuthenticationTokenAdapter
  implements UserPasswordAuthenticationTokenPort {

  private final AuthenticationManager authenticationManager;

  @Override
  public void authenticate(
    final String email,
    final String password
  ) {
    this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        email,
        password
      )
    );
  }
}
