package me.kvdpxne.covilo.shared;

public final class EmailValidationFailedException
  extends RuntimeException {

  EmailValidationFailedException(
    final String message,
    final String invalidEmail
  ) {
    super(message);
  }
}
