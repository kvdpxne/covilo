package me.kvdpxne.covilo.infrastructure.security.jwt;

public class JwtException
  extends RuntimeException {

  public JwtException(final String message) {
    super(message);
  }
}
