package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 * <p>
 * This exception is thrown when attempting to retrieve information about a user
 * that does not exist in the system.
 */
public final class UserNotFoundException
  extends UserException {

  /**
   * Constructs a new {@link UserNotFoundException} with the specified detail
   * message.
   *
   * @param message The detail message.
   */
  public UserNotFoundException(
    final String message
  ) {
    super(message);
  }
}
