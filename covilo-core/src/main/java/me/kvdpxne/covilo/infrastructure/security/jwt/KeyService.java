package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.OctetPrivateJwk;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * This class is used to manage and store key pairs used for JSON Web Tokens
 * (JWT).
 *
 * @since 0.1.0
 */
@Slf4j
@Service
public class KeyService {

  /**
   * The name of the file containing the private key.
   */
  @Value("${application.security.key.file}")
  private String fileName;

  /**
   * The algorithm used for the key pair.
   */
  @Value("${application.security.key.algorithm}")
  private KeyAlgorithm usedAlgorithm;

  public OctetPrivateJwk<PrivateKey, PublicKey> _buildPrivateJwk(
    final PrivateKey privateKey
  ) {
    return Jwks.builder()
      .octetKey(privateKey)
      .idFromThumbprint()
      .build();
  }

  /**
   * Loads the key pair from the specified file.
   *
   * @return The loaded key pair.
   */
  @Cacheable("key_pair")
  public OctetPrivateJwk<PrivateKey, PublicKey> loadPrivateJwk() {
    final Path path = Path.of(this.fileName);
    if (Files.notExists(path)) {
      throw new KeyFileAccessException(
        STR."Key file does not exist: \{this.fileName}."
      );
    }
    final byte[] bytes;
    try (final InputStream input = Files.newInputStream(path)) {
      final int size = input.available();
      if (0 == size) {
        throw new KeyFileAccessException(
          ""
        );
      }
      bytes = new byte[size];
      if (-1 == input.read(bytes, 0, bytes.length)) {
        // TODO handling needed
      }
    } catch (final IOException exception) {
      throw new KeyFileException(
        "",
        exception
      );
    }

    final PKCS8EncodedKeySpec keySpecification = new PKCS8EncodedKeySpec(bytes);
    final KeyFactory keyFactory = this.usedAlgorithm.getKeyFactory();

    try {
      return this._buildPrivateJwk(
        keyFactory.generatePrivate(keySpecification)
      );
    } catch (final InvalidKeySpecException exception) {
      throw new KeyException(
        "",
        exception
      );
    }
  }

  /**
   * Stores the private key to the specified file.
   *
   * @param bytes The byte array representing the private key.
   */
  public void storePrivateKey(
    final byte[] bytes
  ) {
    final Path path = Path.of(this.fileName);

    try {
      Files.write(path, bytes);
    } catch (final IOException exception) {
      throw new KeyFileException(
        "",
        exception
      );
    }
  }

  /**
   * Stores the private key to the specified file.
   *
   * @param privateKey The private key to store.
   */
  public void storePrivateKey(
    final PrivateKey privateKey
  ) {
    this.storePrivateKey(privateKey.getEncoded());
  }

  /**
   * Generates a new private key based on the configured algorithm.
   *
   * @return The generated private key.
   */
  public PrivateKey generatePrivateKey() {
    //
    final Curve curve = switch (this.usedAlgorithm) {
      case Ed25519 -> Jwks.CRV.Ed25519;
      case Ed448 -> Jwks.CRV.Ed448;
      case X25519 -> Jwks.CRV.X25519;
      case X448 -> Jwks.CRV.X448;
      case null -> throw new IllegalStateException(
        "Unsupported key algorithm."
      );
    };

    //
    return curve.keyPair()
      .build()
      .getPrivate();
  }

  /**
   * Generates a new private key based on the configured algorithm and stores it.
   */
  public PrivateKey generatePrivateKeyAndStore() {
    final PrivateKey privateKey = this.generatePrivateKey();
    this.storePrivateKey(privateKey);
    return privateKey;
  }
}
