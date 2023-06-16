package me.kvdpxne.covilo.domain.exception;

public class UserAlreadyExistsException extends Exception {

  public UserAlreadyExistsException() {
  }

  public UserAlreadyExistsException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }

}
