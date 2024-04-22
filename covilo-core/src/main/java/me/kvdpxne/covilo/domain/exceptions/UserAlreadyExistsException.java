package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when attempting to create a user that already exists.
 * <p>
 * This exception is thrown when trying to create a new user with an identifier
 * or email address that already exists in the system.
 */
public final class UserAlreadyExistsException
  extends UserException {

  /**
   * Constructs a new {@link UserAlreadyExistsException} with the specified detail
   * message.
   *
   * @param message The detail message.
   */
  public UserAlreadyExistsException(
    final String message
  ) {
    super(message);
  }
}
