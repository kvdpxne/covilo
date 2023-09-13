package me.kvdpxne.covilo.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.payload.LoginRequest;
import me.kvdpxne.covilo.application.payload.SignupRequest;
import me.kvdpxne.covilo.application.dto.TokenDto;
import me.kvdpxne.covilo.application.UserLifecycleUseCase;
import me.kvdpxne.covilo.domain.exception.AuthenticationException;
import me.kvdpxne.covilo.domain.exception.InvalidPasswordException;
import me.kvdpxne.covilo.domain.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.Role;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import me.kvdpxne.covilo.infrastructure.jwt.TokenGeneratorService;
import me.kvdpxne.covilo.infrastructure.jwt.TokenService;
import me.kvdpxne.covilo.shared.EmailValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public final class UserAuthenticationService {

  private final UserRepository repository;

  private final UserLifecycleUseCase userLifecycleUseCase;

  private final TokenRepository tokenRepository;

  private final TokenGeneratorService tokenGeneratorService;

  private final TokenService tokenService;

  private final AuthenticationManager authenticationManager;

  public TokenDto signup(
    final SignupRequest request
  ) throws AuthenticationException {

    EmailValidator.checkEmail(request.getEmail());

    var password = request.getPassword();
    var confirmPassword = request.getConfirmPassword();

    if (!password.equals(confirmPassword)) {
      throw new InvalidPasswordException();
    }

    // TODO remove
    var role = request.getRole();
    role = null == role ? Role.USER : role;

    var gender = request.getGender();
    gender = null == gender ? Gender.MALE : gender;

    var brithDate = request.getBirthDate();
    brithDate = null == brithDate ? LocalDate.now() : brithDate;

    User user = User.builder()
      .identifier(UUID.randomUUID())
      .firstName(request.getFirstname())
      .lastName(request.getLastname())
      .email(request.getEmail())
      .password(password)
      .role(role)
      .gender(gender)
      .birthDate(brithDate)
      .build();

    try {
      //
      //
      user = this.userLifecycleUseCase.createUser(user);
    } catch (final UserAlreadyExistsException exception) {
      throw new AuthenticationException();
    }

    final String token = this.tokenGeneratorService.createCompactToken(user);
    final String refreshToken = this.tokenGeneratorService.createCompactRefreshToken(user);

    //
    this.insertUserToken(user, token);

    return TokenDto.builder()
      .accessToken(token)
      .refreshToken(refreshToken)
      .build();
  }

  public TokenDto login(
    final LoginRequest request
  ) throws UserNotFoundException {


    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );

    final User user = this.userLifecycleUseCase.getUserByEmail(request.email());

    final String token = this.tokenGeneratorService.createCompactToken(user);
    final String refreshToken = this.tokenGeneratorService.createCompactRefreshToken(user);

    revokeAllUserTokens(user);
    insertUserToken(user, token);
    return TokenDto.builder()
      .accessToken(token)
      .refreshToken(refreshToken)
      .build();
  }

  private void insertUserToken(final User user, final String compactToken) {
    final Token token = Token.builder()
      .identifier(UUID.randomUUID())
      .token(compactToken)
      .tokenType(TokenType.BEARER)
      .user(user)
      .revoked(false)
      .expired(false)
      .build();

    this.tokenRepository.insertToken(token);
  }

  private void revokeAllUserTokens(final User user) {
    Collection<Token> tokens = this.tokenRepository.findValidTokensByUserIdentifier(user.identifier());

    if (tokens.isEmpty()) {
      return;
    }

    tokens = tokens.stream()
      .map(token -> token.toBuilder()
        .revoked(true)
        .expired(true)
        .build()
      )
      .toList();

    this.tokenRepository.insertTokens(tokens);
  }

  public HttpStatus refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      return HttpStatus.UNAUTHORIZED;
    }

    final String refreshToken = header.substring(7);
    final Token token = this.tokenRepository.findTokenByTokenOrNull(refreshToken);

    System.out.println(token);

    if (null != token && token.revoked()) {
      return HttpStatus.FORBIDDEN;
    }

    final String email = tokenService.extractUsername(refreshToken);

    if (email != null) {


      var user = this.repository.findUserByEmailOrNull(email);
      if (tokenService.isTokenValid(refreshToken, user)) {
        var accessToken = tokenGeneratorService.createCompactToken(user);

        revokeAllUserTokens(user);
        insertUserToken(user, accessToken);

        var authResponse = TokenDto.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
    return HttpStatus.OK;
  }
}
