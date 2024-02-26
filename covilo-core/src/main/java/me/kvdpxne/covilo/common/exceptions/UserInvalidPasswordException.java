package me.kvdpxne.covilo.common.exceptions;

public class UserInvalidPasswordException
  extends RuntimeException {

  public UserInvalidPasswordException() {
  }

  public UserInvalidPasswordException(final String message) {
    super(message);
  }

  public UserInvalidPasswordException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
