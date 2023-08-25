package me.kvdpxne.covilo.domain.exception;

public class UserNotFoundException extends UserException {

  public UserNotFoundException() {
  }

  public UserNotFoundException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }
}
