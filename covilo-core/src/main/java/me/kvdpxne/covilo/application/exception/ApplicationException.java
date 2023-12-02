package me.kvdpxne.covilo.application.exception;

public class ApplicationException extends RuntimeException {

  public ApplicationException() {
  }

  public ApplicationException(final String message) {
    super(message);
  }

  public ApplicationException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }

  public ApplicationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ApplicationException(final Throwable cause) {
    super(cause);
  }
}
