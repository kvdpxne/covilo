package me.kvdpxne.covilo.domain.exception;

public class UserException extends Exception {

  public UserException() {
  }

  public UserException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }
}
