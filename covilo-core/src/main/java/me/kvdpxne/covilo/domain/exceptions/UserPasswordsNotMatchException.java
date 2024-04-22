package me.kvdpxne.covilo.domain.exceptions;

public class UserPasswordsNotMatchException extends RuntimeException {

  public UserPasswordsNotMatchException(final String message) {
    super(message);
  }
}
