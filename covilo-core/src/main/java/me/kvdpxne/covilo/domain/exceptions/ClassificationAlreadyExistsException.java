package me.kvdpxne.covilo.domain.exceptions;

/**
 * Exception thrown when attempting to create a classification already exists.
 * <p>
 * This exception is thrown to indicate that an attempt was made to create a
 * classification with an identifier or name that already exists in the system.
 */
public final class ClassificationAlreadyExistsException
  extends RuntimeException {

  /**
   * Constructs a new {@link ClassificationAlreadyExistsException} with the
   * specified detail message.
   *
   * @param message The detail message.
   */
  public ClassificationAlreadyExistsException(
    final String message
  ) {
    super(message);
  }
}
