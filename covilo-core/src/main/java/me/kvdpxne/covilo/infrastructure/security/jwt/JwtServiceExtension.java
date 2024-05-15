package me.kvdpxne.covilo.infrastructure.security.jwt;

import me.kvdpxne.covilo.domain.model.TokenPair;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.shared.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Extension of JwtService providing additional functionality.
 *
 * @since 0.1.0
 */
@Service
public final class JwtServiceExtension
  extends JwtService {

  /**
   * Constructs a {@link JwtServiceExtension} with the provided
   * {@link KeyService}.
   *
   * @param keyService The KeyService instance.
   */
  @Autowired
  public JwtServiceExtension(
    final KeyService keyService
  ) {
    super(keyService);
  }

  /**
   * Builds a {@link TokenPair} for the given user containing access and refresh
   * tokens.
   *
   * @param user The user for whom the tokens are created.
   * @return The {@link TokenPair} containing access and refresh tokens.
   */
  public TokenPair buildTokenPair(
    final User user
  ) {
    Validation.check(
      user,
      () -> "User must not be null."
    );
    var f = TokenPair.builder()
      .withAccessToken(this.createAccessJws(user))
      .withRefreshToken(this.createRefreshJws(user))
      .withExpiry(this.getAccessTokenExpiryMoment())
      .withTokenType(TokenType.BEARER)
      .build();

    System.out.println(f);

    return f;
  }
}
