package me.kvdpxne.covilo.infrastructure.security.jwt;

import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.Jwks;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import org.springframework.stereotype.Component;

/**
 * Utility class for generating and storing keys for use with JJWT.
 *
 * @since 0.1.0
 */
@Component
public class JjwtKeyGenerator {

  /**
   * Stores the private key to the specified file.
   *
   * @param fileName The name of the file to store the key in.
   * @param key      The private key to store.
   */
  private void storeKey(
    final String fileName,
    final Key key
  ) {
    final byte[] bytes = key.getEncoded();
    final Path path = Path.of(fileName);
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
   * Generates a new private key based on the configured algorithm.
   *
   * @param keyAlgorithm The algorithm to use for key generation.
   * @return The generated private key.
   */
  public Key generateKey(
    final KeyAlgorithm keyAlgorithm
  ) {
    final Curve curve = switch (keyAlgorithm) {
      case Ed25519 -> Jwks.CRV.Ed25519;
      case Ed448 -> Jwks.CRV.Ed448;
      case X25519 -> Jwks.CRV.X25519;
      case X448 -> Jwks.CRV.X448;
      case null -> throw new IllegalStateException(
        "Unsupported key algorithm."
      );
    };
    return curve.keyPair()
      .build()
      .getPrivate();
  }

  /**
   * Generates a new private key based on the configured algorithm and stores it
   * in a file.
   *
   * @param keyAlgorithm The algorithm to use for key generation.
   * @param fileName     The name of the file to store the key in.
   * @return The generated private key.
   */
  public Key generateKeyAndStore(
    final KeyAlgorithm keyAlgorithm,
    final String fileName
  ) {
    final Key key = this.generateKey(keyAlgorithm);
    this.storeKey(STR."\{fileName}.key", key);
    return key;
  }
}
