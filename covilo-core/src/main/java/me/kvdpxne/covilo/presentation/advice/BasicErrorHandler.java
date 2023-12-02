package me.kvdpxne.covilo.presentation.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BasicErrorHandler
  extends ResponseEntityExceptionHandler {

  @SuppressWarnings("unchecked")
  protected <T> ResponseEntity<T> buildErrorMessageResponse(
    final Throwable exception,
    final HttpStatus status
    ) {
    return (ResponseEntity<T>) ResponseEntity.status(status).body(
      ErrorMessage.of(
        status.value(),
        status.getReasonPhrase(),
        exception.getMessage()
      )
    );
  }
//
//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  protected ResponseEntity<ErrorMessage> handleUnhandledException(
//    final Throwable exception
//  ) {
//    return this.buildErrorMessageResponse(
//      exception,
//      HttpStatus.INTERNAL_SERVER_ERROR
//    );
//  }
//
//  @Override
//  protected ResponseEntity<Object> handleExceptionInternal(
//    final Exception ex,
//    final Object body,
//    final HttpHeaders headers,
//    final HttpStatusCode statusCode,
//    final WebRequest request
//  ) {
//    return this.buildErrorMessageResponse(
//      ex,
//      HttpStatus.resolve(statusCode.value())
//    );
//  }
}
