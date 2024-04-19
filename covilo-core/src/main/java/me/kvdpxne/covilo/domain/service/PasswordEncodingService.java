package me.kvdpxne.covilo.domain.service;

/**
 * Represents a service for encoding and matching passwords.
 */
public interface PasswordEncodingService {

  /**
   * Encodes the given raw password.
   *
   * @param rawPassword The raw password to encode.
   * @return The encoded password.
   * @throws IllegalArgumentException If the raw password is null or empty.
   */
  String encode(
    final String rawPassword
  );

  /**
   * Matches the given raw password with the encoded password.
   *
   * @param rawPassword     The raw password to check.
   * @param encodedPassword The encoded password to match against.
   * @return {@code true} if the passwords match, otherwise {@code false}.
   * @throws IllegalArgumentException If the raw password or encoded password
   *                                  is {@code null} or empty.
   */
  boolean matches(
    final String rawPassword,
    final String encodedPassword
  );
}
