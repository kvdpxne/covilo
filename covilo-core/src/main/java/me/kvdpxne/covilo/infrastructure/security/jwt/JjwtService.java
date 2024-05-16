package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.security.PrivateKey;
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
public class JjwtService {

  /**
   * Service for managing cryptographic keys.
   */
  private final KeyService keyService;

  /**
   *
   */
  private final JjwtKeyLocatorAdapter jjwtKeyLocatorAdapter;

  /**
   *
   */
//  @Value("${application.security.jjwt.keys}")
//  private Map<String, List<String>> keys;

  /**
   * The time, in seconds, until the access token expires.
   */
  @Value("${application.security.jjwt.expiration}")
  private long accessTokenExpiration;

  /**
   * The time, in milliseconds, until the refresh token expires.
   */
  @Value("${application.security.jjwt.refresh-token-expiration}")
  private long refreshTokenExpiration ;

  /**
   * Constructs a new {@link JjwtService} with the specified key service.
   *
   * @param keyService the key service.
   * @param keyLocatorAdapter
   */
  @Autowired
  public JjwtService(
    final KeyService keyService,
    final JjwtKeyLocatorAdapter keyLocatorAdapter
  ) {
    this.keyService = keyService;
    this.jjwtKeyLocatorAdapter = keyLocatorAdapter;
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

    return Jwts.builder()
      .claims()
      .id(UUID.randomUUID().toString())
      .issuer("Covilo")
      .subject(user.getIdentifier().toString())
      .issuedAt(issuedAt)
      .notBefore(issuedAt)
      .expiration(expiryAt)
      .and();
  }

  /**
   * Creates a JSON Web Signature (JWS) for the given user and expiry time.
   *
   * @param user          The user for whom the token is created.
   * @param expiryTime    The expiry time for the token.
   * @param keyIdentifier The identifier for the key used to encrypt the token.
   * @param keyAlgorithm  The algorithm used for key generation.
   * @return The JWS token.
   */
  private String createJws(
    final User user,
    final long expiryTime,
    final String keyIdentifier,
    final KeyAlgorithm keyAlgorithm
  ) {
    var key = this.keyService._buildPrivateJwk(
      this.keyService.loadKey(keyIdentifier, keyAlgorithm)
    );

    return this.getJwtBuilder(user, expiryTime)
      .header()
      .keyId(keyIdentifier)
      .and()
      .signWith(key.toKey(), Jwts.SIG.EdDSA)
      .compact();
  }

  /**
   * Creates an access JSON Web Signature (JWS) for the given user.
   *
   * @param user The user for whom the access token is created.
   * @return The access JWS token.
   */
  public String createAccessJws(
    final User user
  ) {
    return this.createJws(
      user,
      this.accessTokenExpiration,
      "ed25519_05_2024",
      KeyAlgorithm.Ed25519
    );
  }

  /**
   * Creates a refresh JSON Web Signature (JWS) for the given user.
   *
   * @param user The user for whom the refresh token is created.
   * @return The refresh JWS token.
   */
  public String createRefreshJws(
    final User user
  ) {
    return this.createJws(
      user,
      this.refreshTokenExpiration,
      "ed448_05_2024_refresh",
      KeyAlgorithm.Ed448
    );
  }

  /**
   * Creates a JSON Web Encryption (JWE) for the given user and expiry time.
   *
   * @param user          The user for whom the token is created.
   * @param expiryTime    The expiry time for the token.
   * @param keyIdentifier The identifier for the key used to encrypt the token.
   * @param keyAlgorithm  The algorithm used for key generation.
   * @return The JWE token.
   */
  private String createJwe(
    final User user,
    final long expiryTime,
    final String keyIdentifier,
    final KeyAlgorithm keyAlgorithm
  ) {
    var key = this.keyService._buildPrivateJwk(
      this.keyService.loadKey(keyIdentifier, keyAlgorithm)
    );

    var alg = Jwts.KEY.ECDH_ES_A256KW;
    var enc = Jwts.ENC.A256GCM;

    return this.getJwtBuilder(user, expiryTime)
      .header()
      .keyId(keyIdentifier)
      .and()
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
    return this.createJwe(
      user,
      this.accessTokenExpiration,
      "x25519_05_2024",
      KeyAlgorithm.X25519
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
    return this.createJwe(
      user,
      this.refreshTokenExpiration,
      "x25519_05_2024_refresh",
      KeyAlgorithm.X25519
    );
  }

  /**
   * Parses and verifies a JSON Web Signature (JWS) token.
   *
   * @param jws The JWS token to parse and verify.
   * @return The claims contained in the JWS token.
   */
  public Claims readJws(
    final String jws
  ) {
    return Jwts.parser()
      .keyLocator(this.jjwtKeyLocatorAdapter)
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
    return Jwts.parser()
      .keyLocator(this.jjwtKeyLocatorAdapter)
      .build()
      .parseEncryptedClaims(jwe)
      .getPayload();
  }
}
