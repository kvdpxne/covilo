package me.kvdpxne.covilo.common.exceptions;

public class UserException extends ApplicationException {

  public UserException() {
    this("An undefined exception occurred during an operation on the user.");
  }

  public UserException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }

  public UserException(final Throwable cause) {
    super(cause);
  }

  public UserException(final String message, final Throwable cause) {
    super(message, cause, false, true);
  }

}
