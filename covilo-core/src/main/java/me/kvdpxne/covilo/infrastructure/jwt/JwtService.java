package me.kvdpxne.covilo.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.port.out.ITokenService;
import me.kvdpxne.covilo.common.exceptions.TokenExpiredException;
import me.kvdpxne.covilo.common.exceptions.TokenSignatureException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.infrastructure.configuration.ApplicationConfiguration;
import me.kvdpxne.covilo.shared.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class JwtService
  implements ITokenService {

  /**
   * General configuration for the entire application.
   */
  private final ApplicationConfiguration applicationConfiguration;

  /**
   * Configuration application associated with JWT.
   */
  private final JwtConfiguration tokenConfiguration;

  private Key getKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(
      this.tokenConfiguration.getSecretKey()
    ));
  }

  /**
   * @throws NullPointerException     If the given user is null.
   * @throws IllegalArgumentException If the given compact token expiration
   *                                  time is equal to or less than zero.
   */
  private String createCompactToken(
    final User user,
    final long expiration
  ) {
    Validation.check(
      user,
      "The given user cannot be null."
    );
    Validation.check(
      0 >= expiration,
      "The expiry time cannot be equal to or less than zero."
    );

    // The algorithm signature that will be signed for each compact token
    // created.
    final SignatureAlgorithm signature = SignatureAlgorithm.HS256;

    final Header<?> header = Jwts.jwsHeader()
      .setAlgorithm(signature.getValue())
      .setType(Header.JWT_TYPE);

    // The current system time in milliseconds needed to store the creation
    // date of the compact token and calculate its expiration time.
    final long current = System.currentTimeMillis();
    final Date currentDate = new Date(current);

    final Claims claims = Jwts.claims()
      .setIssuer(this.applicationConfiguration.getName())
      .setSubject(user.email())
      .setAudience(user.identifier().toString())
      .setIssuedAt(currentDate)
      .setExpiration(new Date(current + expiration))
      .setNotBefore(currentDate);

    final JwtBuilder builder = Jwts.builder()
      .setHeader((Map<String, Object>) header)
      .setClaims(claims);

    try {
      builder.signWith(this.getKey(), signature);
    } catch (final InvalidKeyException ignore) {
      // TODO JWT InvalidKeyException
    }

    return builder.compact();
  }

  @Override
  public String createCompactAccessToken(final User user) {
    // Creates a compact access token with a defined expiration time.
    return this.createCompactToken(
      user,
      this.tokenConfiguration.getAccessTokenExpiration()
    );
  }

  @Override
  public String createCompactRefreshToken(final User user) {
    // Creates a compact refresh token with a defined expiration time.
    return this.createCompactToken(
      user,
      this.tokenConfiguration.getRefreshTokenExpiration()
    );
  }

  /**
   * @throws NullPointerException     If the given compact token is null.
   * @throws IllegalArgumentException If the given compact token is empty or
   *                                  contains only space.
   * @throws TokenSignatureException  If the signature of the given
   *                                  compact token is different than
   *                                  the one defined in the application
   *                                  configuration.
   * @throws TokenExpiredException    If the compact token has expired.
   */
  private Claims extractClaims(
    final String compactToken
  ) throws TokenSignatureException, TokenExpiredException {
    Validation.check(
      compactToken,
      "The given compact token cannot be null."
    );
    Validation.check(
      compactToken.isBlank(),
      "The given compact token cannot be empty or contains only space."
    );

    final JwtParser parser = Jwts.parserBuilder()
      .setSigningKey(this.getKey())
      .build();

    try {
      return parser.parseClaimsJws(compactToken).getBody();
    } catch (final SignatureException exception) {
      // Generally, an ordinary user should not be able to send a token other
      // than the one with a trusted signature, so this is an additional
      // security against sending untrusted signatures.
      throw new TokenSignatureException(
        "The compact token signature is not trusted.",
        exception
      );
    } catch (final ExpiredJwtException exception) {
      throw new TokenExpiredException(
        "The compact token is expired.",
        exception
      );
    }
  }

  private <T> T extractClaims(
    final String compactToken,
    final Function<Claims, T> function
  ) {
    return function.apply(this.extractClaims(compactToken));
  }

  @Override
  public String extractAudience(
    final String compactToken
  ) throws TokenSignatureException, TokenExpiredException {
    return this.extractClaims(compactToken, Claims::getAudience);
  }

  @Override
  public String extractSubject(
    final String compactToken
  ) throws TokenSignatureException, TokenExpiredException {
    return this.extractClaims(compactToken, Claims::getSubject);
  }
}
