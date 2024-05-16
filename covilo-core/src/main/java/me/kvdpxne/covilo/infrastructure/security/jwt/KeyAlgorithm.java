package me.kvdpxne.covilo.infrastructure.security.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * Enum representing key factory algorithms.
 *
 * @since 0.1.0
 */
public enum KeyAlgorithm {

  /**
   * Ed25519 key factory algorithm for generating and processing Ed25519 keys.
   * It is based on elliptic curve cryptography and offers strong security
   * with relatively small key sizes.
   */
  Ed25519("Ed25519"),

  /**
   * Ed448 key factory algorithm for generating and processing Ed448 keys.
   * Similar to Ed25519, Ed448 is also based on elliptic curve cryptography,
   * but it provides even higher security by using larger key sizes and different
   * elliptic curve parameters.
   */
  Ed448("Ed448"),

  /**
   * X25519 key factory algorithm for generating and processing X25519 keys.
   * X25519 is a Diffie-Hellman key exchange algorithm using Curve25519,
   * providing strong security with relatively small key sizes.
   */
  X25519("X25519"),

  /**
   * X448 key factory algorithm for generating and processing X448 keys.
   * X448 is a Diffie-Hellman key exchange algorithm using Curve448,
   * providing high security with larger key sizes.
   */
  X448("X448");

  /**
   * The algorithm name.
   */
  private final String algorithm;

  /**
   * Constructs a new {@link KeyAlgorithm} enum value with the
   * specified algorithm name.
   *
   * @param algorithm The name of the algorithm.
   */
  KeyAlgorithm(
    final String algorithm
  ) {
    this.algorithm = algorithm;
  }

  /**
   * Returns the algorithm name.
   *
   * @return The algorithm name.
   */
  public String getAlgorithm() {
    return this.algorithm;
  }

  /**
   * Returns a {@link KeyFactory} instance for the algorithm.
   *
   * @return A {@link KeyFactory} instance.
   */
  public KeyFactory getKeyFactory() {
    try {
      return KeyFactory.getInstance(this.algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
