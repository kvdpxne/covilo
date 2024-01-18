package me.kvdpxne.covilo.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.port.out.UserAuthenticationServicePort;
import me.kvdpxne.covilo.application.dto.TokenDto;
import me.kvdpxne.covilo.common.exceptions.UserInvalidEmailAddressException;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.common.exceptions.TokenException;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.application.mapper.ITokenMapper;
import me.kvdpxne.covilo.application.payload.LoginRequest;
import me.kvdpxne.covilo.application.payload.SignupRequest;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.infrastructure.security.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = Endpoints.USER_AUTHENTICATION)
@RestController
public final class UserAuthenticationController {

  private final UserAuthenticationServicePort userAuthenticationService;
  private final ITokenMapper tokenMapper;

  @PostMapping("/register")
  public ResponseEntity<TokenDto> signup(
    @Validated @RequestBody final SignupRequest request
  ) throws UserAlreadyExistsException {
    final Token token;
    try {
      token = this.userAuthenticationService.createAuthentication(request);
    } catch (final UserInvalidPasswordException | UserInvalidEmailAddressException exception) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
    return ResponseEntity.ok(
      this.tokenMapper.toTokenDto(token)
    );
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(
    @Validated @RequestBody final LoginRequest request
  ) {
    return ResponseEntity.ok(
      this.userAuthenticationService.authenticate(request)
    );
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws UserNotFoundException, IOException {

    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String prefix = Constants.PREFIX;

    if (null == header || !header.startsWith(prefix)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    final String compactToken = header.substring(prefix.length());

    System.out.printf("TOKEN: %s%nCOMPACT TOKEN: %s%n", header, compactToken);
    final Token token;

    try {
      token = this.userAuthenticationService.refreshAuthentication(compactToken);
    } catch (final TokenException cause) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    if (null == token || token.revoked()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    new ObjectMapper().writeValue(
      response.getOutputStream(),
      new TokenDto(token.compactToken(), compactToken)
    );

    return ResponseEntity.ok().build();
  }
}
