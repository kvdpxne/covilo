package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when attempting to create a category that already exists.
 * <p>
 * This exception is thrown to indicate that an attempt was made to create a
 * category with an identifier or name that already exists in the system.
 */
public final class CategoryAlreadyExistsException
  extends RuntimeException {

  /**
   * Constructs a new {@link CategoryAlreadyExistsException} with the specified
   * detail message.
   *
   * @param message The detail message.
   */
  public CategoryAlreadyExistsException(
    final String message
  ) {
    super(message);
  }
}
