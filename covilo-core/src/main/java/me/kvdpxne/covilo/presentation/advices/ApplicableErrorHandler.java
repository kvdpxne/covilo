package me.kvdpxne.covilo.presentation.advices;

import me.kvdpxne.covilo.domain.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.infrastructure.storage.StorageException;
import me.kvdpxne.covilo.infrastructure.storage.StorageFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicableErrorHandler
  extends BasicErrorHandler {

  @ExceptionHandler(StorageException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorMessage> handleStorageException(
    final StorageException exception
  ) {
    return this.buildErrorMessageResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> handleStorageFileNotFoundException(
    final StorageFileNotFoundException exception
  ) {
    return this.buildErrorMessageResponse(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(
    final UserAlreadyExistsException exception
  ) {
    return this.buildErrorMessageResponse(exception, HttpStatus.CONFLICT);
  }
}
