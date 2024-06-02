package me.kvdpxne.covilo.presentation;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.service.AuthenticationService;
import me.kvdpxne.covilo.domain.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.presentation.dto.TokenPairDto;
import me.kvdpxne.covilo.presentation.payloads.LoginRequest;
import me.kvdpxne.covilo.presentation.payloads.SignupRequest;
import me.kvdpxne.covilo.infrastructure.security.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = Endpoints.USER_AUTHENTICATION)
@RestController
public final class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public TokenPairDto signup(
    @Valid
    @RequestBody
    final SignupRequest request
  ) {
    final var user = User.builder()
      .withEmail(request.email())
      .withPassword(request.password())
      .withFirstName(request.firstName())
      .withLastName(request.lastName())
      .withGender(request.gender())
      .withBrithDate(request.birthDate())
      .build();

    return TokenPairDto.fromTokenPair(
      this.authenticationService.signup(user)
    );
  }

  @PostMapping("/login")
  public TokenPairDto login(
    @Valid
    @RequestBody
    final LoginRequest request
  ) {
    return TokenPairDto.fromTokenPair(
      this.authenticationService.login(
        request.email(),
        request.password()
      )
    );
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws UserNotFoundException, IOException {

    final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    final var prefix = Constants.PREFIX;

    if (null == header || !header.startsWith(prefix)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    JsonMapper.builder()
      .findAndAddModules()
      .build()
      .writeValue(
        response.getOutputStream(),
        this.authenticationService.refreshAccessToken(
          header.substring(prefix.length())
        )
      );

    return ResponseEntity.ok().build();
  }
}
