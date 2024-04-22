package me.kvdpxne.covilo.domain.exceptions;

public class TokenSignatureException extends TokenException {

  public TokenSignatureException() {
  }

  public TokenSignatureException(final String message) {
    super(message);
  }

  public TokenSignatureException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenSignatureException(final Throwable cause) {
    super(cause);
  }
}
