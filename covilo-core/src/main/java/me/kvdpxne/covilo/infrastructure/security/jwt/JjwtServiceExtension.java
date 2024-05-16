package me.kvdpxne.covilo.infrastructure.security.jwt;

import me.kvdpxne.covilo.domain.model.TokenPair;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.shared.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link JjwtService} providing additional functionality.
 *
 * @since 0.1.0
 */
@Service
public class JjwtServiceExtension
  extends JjwtService {

  /**
   * Constructs a {@link JjwtServiceExtension} with the provided
   * {@link KeyService} and {@link JjwtKeyLocatorAdapter}.
   *
   * @param keyService            The {@link KeyService} instance.
   * @param jjwtKeyLocatorAdapter The {@link JjwtKeyLocatorAdapter} instance.
   */
  @Autowired
  public JjwtServiceExtension(
    final KeyService keyService,
    final JjwtKeyLocatorAdapter jjwtKeyLocatorAdapter
  ) {
    super(keyService, jjwtKeyLocatorAdapter);
  }

  /**
   * Builds a {@link TokenPair} for the given user containing access and refresh
   * tokens.
   *
   * @param user The user for whom the tokens are created.
   * @return The {@link TokenPair} containing access and refresh tokens.
   * @throws NullPointerException If the user is null.
   */
  public TokenPair buildTokenPair(
    final User user
  ) {
    Validation.check(
      user,
      () -> "User must not be null."
    );
    return TokenPair.builder()
      .withAccessToken(this.createAccessJws(user))
      .withRefreshToken(this.createRefreshJwe(user))
      .withExpiry(this.getAccessTokenExpiryMoment())
      .withTokenType(TokenType.BEARER)
      .build();
  }
}
