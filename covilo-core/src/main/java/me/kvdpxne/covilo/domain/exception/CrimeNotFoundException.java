package me.kvdpxne.covilo.domain.exception;

public class CrimeNotFoundException extends Exception {

  public CrimeNotFoundException() {
  }

  public CrimeNotFoundException(String message) {
    super(message);
  }
}
