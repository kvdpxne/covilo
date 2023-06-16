package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class TestController {

  @GetMapping
  @PreAuthorize("hasAuthority('READ')")
  public String get() {
    return "GET:: admin controller";
  }
  @PostMapping
  @PreAuthorize("hasAuthority('CREATE')")
  public String post() {
    return "POST:: admin controller";
  }
  @PutMapping
  @PreAuthorize("hasAuthority('update')")
  public String put() {
    return "PUT:: admin controller";
  }
  @DeleteMapping
  @PreAuthorize("hasAuthority('DELETE')")
  public String delete() {
    return "DELETE:: admin controller";
  }
}
