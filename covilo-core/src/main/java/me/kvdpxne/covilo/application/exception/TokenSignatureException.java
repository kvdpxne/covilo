package me.kvdpxne.covilo.application.exception;

public class TokenSignatureException extends TokenException {

  public TokenSignatureException() {
  }

  public TokenSignatureException(final String message) {
    super(message);
  }

  public TokenSignatureException(final String message, final Object... arguments) {
    super(message, arguments);
  }

  public TokenSignatureException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenSignatureException(final Throwable cause) {
    super(cause);
  }
}
