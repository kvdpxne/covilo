package me.kvdpxne.covilo.infrastructure.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenGeneratorService {

  private final TokenConfiguration tokenConfiguration;

  public String createCompactToken(
    final User user,
    final Map<String, ?> claims,
    final long expiration
  ) {
    //
    final long currentTime = System.currentTimeMillis();

    //
    //
    final JwtBuilder builder = Jwts.builder()
      .setSubject(user.email())
      .setIssuedAt(new Date(currentTime))
      .setExpiration(new Date(currentTime + expiration))
      .signWith(this.tokenConfiguration.getKey(), SignatureAlgorithm.HS256);

    if (null != claims && !claims.isEmpty()) {
      builder.setClaims(claims);
    }

    return builder.compact();
  }

  public String createCompactToken(
    final User user
  ) {
    return this.createCompactToken(
      user,
      null,
      this.tokenConfiguration.getJwtExpiration()
    );
  }

  /**
   *
   */
  public String createCompactRefreshToken(
    final User user
  ) {
    return this.createCompactToken(user, null, this.tokenConfiguration.getJwtExpiration());
  }
}
