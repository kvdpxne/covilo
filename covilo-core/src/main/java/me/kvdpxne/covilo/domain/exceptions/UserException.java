package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception class for general user-related exceptions.
 * <p>
 * This exception serves as a base class for various user-related exceptions. It
 * is used when there is a need to throw an exception related to user
 * operations, without specifying a more specific type of exception.
 */
public class UserException
  extends RuntimeException {

  /**
   * Constructs a new {@link UserException} with the specified detail message.
   *
   * @param message The detail message.
   */
  public UserException(
    final String message
  ) {
    super(message);
  }
}
