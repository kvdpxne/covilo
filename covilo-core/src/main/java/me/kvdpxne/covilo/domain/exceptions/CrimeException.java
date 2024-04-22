package me.kvdpxne.covilo.domain.exceptions;

public class CrimeException extends RuntimeException {

  CrimeException(final String message, final Object... arguments) {
    super(String.format(message, arguments));
  }
}
