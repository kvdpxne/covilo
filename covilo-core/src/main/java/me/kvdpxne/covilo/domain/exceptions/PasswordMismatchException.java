package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when a password mismatch occurs.
 * <p>
 * This exception is thrown when a user attempts to perform an action requiring
 * password verification, but the provided password does not match the expected
 * password.
 */
public final class PasswordMismatchException
  extends InvalidPasswordException {

  /**
   * Constructs a new {@link PasswordMismatchException} with the specified
   * detail message.
   *
   * @param message The detail message.
   */
  public PasswordMismatchException(
    final String message
  ) {
    super(message);
  }
}
