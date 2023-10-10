package me.kvdpxne.covilo.presentation.advice;

import java.time.LocalDateTime;

/**
 *
 *
 */
public record ErrorMessage(
  LocalDateTime timestamp,
  int status,
  String error,
  String message
) {

  public static ErrorMessage of(
    final int status,
    final String error,
    final String message
  ) {
    return new ErrorMessage(
      LocalDateTime.now(),
      status,
      error,
      message
    );
  }
}
