package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when a classification is not found.
 * <p>
 * This exception is thrown to indicate that an attempt was made to retrieve or
 * manipulate a classification that does not exist in the system.
 */
public final class ClassificationNotFoundException
  extends RuntimeException {

  /**
   * Constructs a new {@link ClassificationNotFoundException} with the specified
   * detail message.
   *
   * @param message The detail message.
   */
  public ClassificationNotFoundException(
    final String message
  ) {
    super(message);
  }
}
