package me.kvdpxne.covilo.application.exception;

public class CrimeAlreadyExistsException extends CrimeException {

  public CrimeAlreadyExistsException() {
    super("");
  }

  public CrimeAlreadyExistsException(String message) {
    super(message);
  }
}
