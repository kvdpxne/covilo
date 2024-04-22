package me.kvdpxne.covilo.domain.exceptions;

public class TokenException extends RuntimeException {

  public TokenException() {
  }

  public TokenException(final String message) {
    super(message);
  }

  public TokenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenException(final Throwable cause) {
    super(cause);
  }
}
