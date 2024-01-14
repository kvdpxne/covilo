package me.kvdpxne.covilo.common.exceptions;

public class TokenExpiredException extends TokenException {

  public TokenExpiredException() {
  }

  public TokenExpiredException(final String message) {
    super(message);
  }

  public TokenExpiredException(final String message, final Object... arguments) {
    super(message, arguments);
  }

  public TokenExpiredException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenExpiredException(final Throwable cause) {
    super(cause);
  }
}
