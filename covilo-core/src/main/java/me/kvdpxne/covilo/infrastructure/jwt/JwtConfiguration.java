package me.kvdpxne.covilo.infrastructure.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfiguration {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  /**
   * The time, in milliseconds, until the access token expires.
   */
  @Value("${application.security.jwt.expiration}")
  private long accessTokenExpiration;

  /**
   * The time, in milliseconds, until the refresh token expires.
   */
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshTokenExpiration;
}
