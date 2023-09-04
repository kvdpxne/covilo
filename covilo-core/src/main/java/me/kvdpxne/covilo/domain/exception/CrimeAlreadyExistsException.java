package me.kvdpxne.covilo.domain.exception;

public class CrimeAlreadyExistsException extends Exception {

  public CrimeAlreadyExistsException() {
  }

  public CrimeAlreadyExistsException(String message) {
    super(message);
  }
}
