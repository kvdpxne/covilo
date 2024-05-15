package me.kvdpxne.covilo.infrastructure.security.jwt;

/**
 * Exception class for handling errors related to accessing cryptographic key
 * files.
 *
 * @since 0.1.0
 */
public class KeyFileAccessException
  extends KeyFileException {

  /**
   * Constructs a new {@link KeyFileAccessException} with the specified detail
   * message.
   *
   * @param message the detail message.
   */
  public KeyFileAccessException(
    final String message
  ) {
    super(message);
  }
}
