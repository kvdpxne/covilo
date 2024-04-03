package me.kvdpxne.covilo.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling status-related requests.
 */
@RestController
@RequestMapping("api/status")
public final class StatusController {

  /**
   * Retrieves the status of the application.
   */
  @GetMapping
  public ResponseEntity<?> getStatus() {
    return ResponseEntity.ok().build();
  }
}
