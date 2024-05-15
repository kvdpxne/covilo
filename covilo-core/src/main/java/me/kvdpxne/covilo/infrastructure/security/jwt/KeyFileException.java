package me.kvdpxne.covilo.infrastructure.security.jwt;

/**
 * Exception class for handling errors related to cryptographic key files.
 *
 * @since 0.1.0
 */
public class KeyFileException
  extends KeyException {

  /**
   * Constructs a new {@link KeyFileException} with the specified detail
   * message.
   *
   * @param message the detail message.
   */
  public KeyFileException(
    final String message
  ) {
    super(message);
  }

  public KeyFileException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }
}
