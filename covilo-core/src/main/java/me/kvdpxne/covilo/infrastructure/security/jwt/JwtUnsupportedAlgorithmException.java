package me.kvdpxne.covilo.infrastructure.security.jwt;

public class JwtUnsupportedAlgorithmException
  extends JwtException {

  public JwtUnsupportedAlgorithmException(final String message) {
    super(message);
  }
}
