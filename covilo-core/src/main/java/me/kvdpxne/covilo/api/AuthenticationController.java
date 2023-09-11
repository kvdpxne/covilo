package me.kvdpxne.covilo.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.payload.LoginRequest;
import me.kvdpxne.covilo.application.payload.SignupRequest;
import me.kvdpxne.covilo.application.dto.TokenDto;
import me.kvdpxne.covilo.domain.exception.AuthenticationException;
import me.kvdpxne.covilo.domain.exception.UserNotFoundException;
import me.kvdpxne.covilo.domain.service.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/0.1.0/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserAuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<TokenDto> register(
    @RequestBody final SignupRequest request
  ) throws AuthenticationException {
    return ResponseEntity.ok(service.signup(request));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) throws UserNotFoundException {
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    return ResponseEntity.status(service.refreshToken(request, response)).build();
  }
}
