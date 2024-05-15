package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.OctetPrivateJwk;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Service class for managing JSON Web Tokens (JWT).
 *
 * @since 0.1.0
 */
public class JwtService {

  /**
   * Service for managing cryptographic keys.
   */
  private final KeyService keyService;

  /**
   * The time, in seconds, until the access token expires.
   */
  @Value("${application.security.jwt.expiration}")
  private long accessTokenExpiration = 1_800;

  /**
   * The time, in milliseconds, until the refresh token expires.
   */
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshTokenExpiration = 86_400;

  /**
   * Constructs a new {@link JwtService} with the specified key service.
   *
   * @param keyService the key service.
   */
  @Autowired
  public JwtService(
    final KeyService keyService
  ) {
    this.keyService = keyService;
  }

  /**
   * Retrieves the moment when the access token expires.
   *
   * @return The moment when the access token expires.
   */
  public Instant getAccessTokenExpiryMoment() {
    return Instant.now().plusSeconds(this.accessTokenExpiration);
  }

  /**
   * Retrieves the moment when the refresh token expires.
   *
   * @return The moment when the refresh token expires.
   */
  public Instant getRefreshTokenExpiryMoment() {
    return Instant.now().plusSeconds(this.refreshTokenExpiration);
  }

  /**
   * Constructs a JwtBuilder with the specified user and expiry time.
   *
   * @param user       the user.
   * @param expiryTime the expiry time.
   * @return A JwtBuilder instance.
   */
  public JwtBuilder getJwtBuilder(
    final User user,
    final long expiryTime
  ) {
    var now = Instant.now();
    var issuedAt = Date.from(now);
    var expiryAt = Date.from(now.plusSeconds(expiryTime));
    var activeAfter = Date.from(now.plusSeconds(1));

    return Jwts.builder()
      .claims()
      .id(UUID.randomUUID().toString())
      .issuer("Covilo")
      .audience()
      .add("covilo_dashboard")
      .add("covilo_website")
      .and()
      .subject(user.getIdentifier().toString())
      .issuedAt(issuedAt)
      .notBefore(activeAfter)
      .expiration(expiryAt)
      .and();
  }

  /**
   * Retrieves the next private JSON Web Key (JWK).
   *
   * @return The next private JWK.
   */
  public OctetPrivateJwk<PrivateKey, PublicKey> getNextPrivateJwk() {
    OctetPrivateJwk<PrivateKey, PublicKey> privateJwk;
    try {
      privateJwk = this.keyService.loadPrivateJwk();
    } catch (final KeyFileAccessException exception) {
      privateJwk = this.keyService._buildPrivateJwk(
        this.keyService.generatePrivateKeyAndStore()
      );
    }
    return privateJwk;
  }

  /**
   * Creates a JSON Web Signature (JWS) for the given user and expiry time.
   *
   * @param user       The user for whom the token is created.
   * @param expiryTime The expiry time for the token.
   * @return The JWS token.
   * @deprecated Use {@link #createAccessJwe(User)} or
   * {@link #createRefreshJwe(User)} instead.
   */
  @Deprecated
  public String _createJws(
    final User user,
    final long expiryTime
  ) {
    var key = this.getNextPrivateJwk();

    return this.getJwtBuilder(user, expiryTime)
      .signWith(key.toKey(), Jwts.SIG.EdDSA)
      .compact();
  }

  /**
   * Creates an access JSON Web Signature (JWS) for the given user.
   *
   * @param user The user for whom the access token is created.
   * @return The access JWS token.
   * @deprecated Use {@link #createAccessJwe(User)} instead.
   */
  @Deprecated
  public String createAccessJws(
    final User user
  ) {
    return this._createJws(
      user,
      this.accessTokenExpiration
    );
  }

  /**
   * Creates a refresh JSON Web Signature (JWS) for the given user.
   *
   * @param user The user for whom the refresh token is created.
   * @return The refresh JWS token.
   * @deprecated Use {@link #createRefreshJwe(User)} instead.
   */
  @Deprecated
  public String createRefreshJws(
    final User user
  ) {
    return this._createJws(
      user,
      this.refreshTokenExpiration
    );
  }

  /**
   * Creates a JSON Web Encryption (JWE) for the given user and expiry time.
   *
   * @param user      The user for whom the token is created.
   * @param expiryTime The expiry time for the token.
   * @return The JWE token.
   */
  public String _createJwe(
    final User user,
    final long expiryTime
  ) {
    var key = this.getNextPrivateJwk();

    var alg = Jwts.KEY.ECDH_ES_A256KW;
    var enc = Jwts.ENC.A256GCM;

    return this.getJwtBuilder(user, expiryTime)
      .encryptWith(key.toKeyPair().getPublic(), alg, enc)
      .compact();
  }

  /**
   * Creates access JSON Web Encryption (JWE) for the given user.
   *
   * @param user The user for whom the access token is created.
   * @return The access JWE token.
   */
  public String createAccessJwe(
    final User user
  ) {
    return this._createJwe(
      user,
      this.accessTokenExpiration
    );
  }

  /**
   * Creates a refresh JSON Web Encryption (JWE) for the given user.
   *
   * @param user The user for whom the refresh token is created.
   * @return The refresh JWE token.
   */
  public String createRefreshJwe(
    final User user
  ) {
    return this._createJwe(
      user,
      this.refreshTokenExpiration
    );
  }

  /**
   * Parses and verifies a JSON Web Signature (JWS) token.
   *
   * @param jws The JWS token to parse and verify.
   * @return The claims contained in the JWS token.
   * @deprecated Use {@link #readJwe(String)} instead.
   */
  public Claims readJws(
    final String jws
  ) {
    var key = this.getNextPrivateJwk();

    return Jwts.parser()
      .verifyWith(key.toKeyPair().getPublic())
      .build()
      .parseSignedClaims(jws)
      .getPayload();
  }

  /**
   * Parses and decrypts a JSON Web Encryption (JWE) token.
   *
   * @param jwe The JWE token to parse and decrypt.
   * @return The claims contained in the JWE token.
   */
  public Claims readJwe(
    final String jwe
  ) {
    var key = this.getNextPrivateJwk();

    return Jwts.parser()
      .decryptWith(key.toKeyPair().getPrivate())
      .build()
      .parseEncryptedClaims(jwe)
      .getPayload();
  }
}
