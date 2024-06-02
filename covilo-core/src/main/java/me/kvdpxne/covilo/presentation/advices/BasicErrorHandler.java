package me.kvdpxne.covilo.presentation.advices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BasicErrorHandler
  extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    final var details = new HashMap<String, List<String>>();
    final var httpStatus = (HttpStatus) status;

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(it -> details.computeIfAbsent(
        it.getField(),
        _ -> new ArrayList<>()
      ).add(it.getDefaultMessage()));

    final var body = ErrorResponse.from(
      httpStatus.value(),
      httpStatus.getReasonPhrase(),
      ex.getBody().getDetail(),
      details.entrySet()
        .stream()
        .map(it -> new ErrorDetails(
          it.getKey(),
          it.getValue().toArray(String[]::new)
        )),
      ((ServletWebRequest) request).getRequest().getRequestURI()
    );

    return ResponseEntity.badRequest().body(body);
  }
}
