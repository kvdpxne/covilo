package me.kvdpxne.covilo.common.exceptions;

public class UserPasswordsNotMatchException extends RuntimeException {

  public UserPasswordsNotMatchException(final String message) {
    super(message);
  }
}
