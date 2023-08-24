package me.kvdpxne.covilo.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.api.request.LoginCredentials;
import me.kvdpxne.covilo.api.request.RegisterRequest;
import me.kvdpxne.covilo.api.response.AuthenticationResponse;
import me.kvdpxne.covilo.application.PasswordEncodingUseCase;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.Role;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.security.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final TokenService jwtService;
  private final AuthenticationManager authenticationManager;

  private final PasswordEncodingUseCase passwordEncodingUseCase;

  public AuthenticationResponse register(
    final RegisterRequest request
  ) throws InvalidEmailAddressException, InvalidPasswordException {
    /* Gmail Special Case for Emails
     *
     * There's one special case that applies only to the Gmail domain: it's
     * permission to use the character + character in the local part of the
     * email. For the Gmail domain, the two email addresses
     * username+something@gmail.com and username@gmail.com are the same.
     *
     * Also, username@gmail.com is similar to user+name@gmail.com.
     *
     * We must implement a slightly different regex that will pass the email
     * validation for this special case as well:
     */
    var pattern = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[^-]"
      + "[A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$";

    var email = request.getEmail();

    if (!email.matches(pattern)) {
      throw new InvalidEmailAddressException();
    }

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

    var user = User.builder()
      .firstName(request.getFirstname())
      .lastName(request.getLastname())
      .email(email)
      .password(this.passwordEncodingUseCase.encode(password))
      .role(role)
      .gender(gender)
      .birthDate(brithDate)
      .build();

    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
      .accessToken(jwtToken)
      .refreshToken(refreshToken)
      .build();
  }

  public AuthenticationResponse authenticate(LoginCredentials request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.recognizableName(),
        request.password()
      )
    );
    var user = repository.findByEmail(request.recognizableName())
      .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
      .accessToken(jwtToken)
      .refreshToken(refreshToken)
      .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
      .user(user)
      .token(jwtToken)
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdentifier());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
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
    final Token token = tokenRepository.findByToken(refreshToken).orElse(null);

    System.out.println(token);

    if (null != token && token.isRevoked()) {
      return HttpStatus.FORBIDDEN;
    }
    final String email = jwtService.extractUsername(refreshToken);
    if (email != null) {
      var user = this.repository.findByEmail(email)
        .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
    return HttpStatus.OK;
  }
}
