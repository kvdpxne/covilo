package me.kvdpxne.covilo.infrastructure.security.jwt;

/**
 * Exception class for handling errors related to cryptographic keys.
 *
 * @since 0.1.0
 */
public class KeyException
  extends RuntimeException {

  /**
   * Constructs a new {@link KeyException} with the specified detail message.
   *
   * @param message the detail message.
   */
  public KeyException(
    final String message
  ) {
    super(message);
  }

  /**
   * Constructs a new {@link KeyException} with the specified detail message
   * and cause.
   *
   * @param message the detail message.
   * @param cause   the cause.
   */
  public KeyException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }
}
