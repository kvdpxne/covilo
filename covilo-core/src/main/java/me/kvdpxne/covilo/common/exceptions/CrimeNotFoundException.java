package me.kvdpxne.covilo.common.exceptions;

public class CrimeNotFoundException extends CrimeException {

  public CrimeNotFoundException() {
    super("");
  }

  public CrimeNotFoundException(String message) {
    super(message);
  }

  public CrimeNotFoundException(final String message, final Object... arguments) {
    super(message, arguments);
  }
}
