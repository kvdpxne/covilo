package me.kvdpxne.covilo.domain.exceptions;

public class AgeRestrictionException extends UserException {

  /**
   * Constructs a new {@link UserException} with the specified detail message.
   *
   * @param message The detail message.
   */
  public AgeRestrictionException(final String message) {
    super(message);
  }
}
