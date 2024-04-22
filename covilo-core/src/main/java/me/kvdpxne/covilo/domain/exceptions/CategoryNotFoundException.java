package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when a category is not found.
 * <p>
 * This exception is thrown to indicate that an attempt was made to retrieve or
 * manipulate a category that does not exist in the system.
 */
public final class CategoryNotFoundException
  extends RuntimeException {

  /**
   * Constructs a new {@link CategoryNotFoundException} with the specified
   * detail message.
   *
   * @param message The detail message.
   */
  public CategoryNotFoundException(
    final String message
  ) {
    super(message);
  }
}
