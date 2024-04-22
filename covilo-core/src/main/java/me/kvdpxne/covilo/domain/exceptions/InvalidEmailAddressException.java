package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when an invalid email address is provided.
 * <p>
 * This exception is thrown when a user attempts to perform an action that
 * requires an email address, but the provided email address does not meet the
 * required format or is otherwise invalid.
 */
public final class InvalidEmailAddressException
  extends UserException {

  /**
   * Constructs a new {@link InvalidEmailAddressException} with the specified
   * detail message.
   *
   * @param message The detail message.
   */
  public InvalidEmailAddressException(
    final String message
  ) {
    super(message);
  }
}
