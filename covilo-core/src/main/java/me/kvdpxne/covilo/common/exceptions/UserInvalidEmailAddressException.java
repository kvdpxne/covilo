package me.kvdpxne.covilo.common.exceptions;

public class UserInvalidEmailAddressException extends RuntimeException {

  public UserInvalidEmailAddressException(final String message) {
    super(message);
  }
}
