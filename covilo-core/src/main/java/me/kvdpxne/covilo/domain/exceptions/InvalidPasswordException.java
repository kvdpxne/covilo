package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when an invalid password is provided.
 * <p>
 * This exception is thrown when a user attempts to perform an action that
 * requires a password, but the provided password does not meet the required
 * criteria or is otherwise invalid.
 */
public class InvalidPasswordException
  extends UserException {

  /**
   * Constructs a new {@link InvalidPasswordException} with the specified detail
   * message.
   *
   * @param message The detail message.
   */
  public InvalidPasswordException(
    final String message
  ) {
    super(message);
  }
}
