package me.kvdpxne.covilo.application.exception;

public class CrimeException extends Exception {

  CrimeException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }
}
