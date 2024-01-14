package me.kvdpxne.covilo.common.exceptions;

public class TokenException extends ApplicationException {

  public TokenException() {
  }

  public TokenException(final String message) {
    super(message);
  }

  public TokenException(final String message, final Object... arguments) {
    super(message, arguments);
  }

  public TokenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenException(final Throwable cause) {
    super(cause);
  }
}
