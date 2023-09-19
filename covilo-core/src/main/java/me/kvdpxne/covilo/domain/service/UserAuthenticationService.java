package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.ITokenService;
import me.kvdpxne.covilo.application.IUserAuthenticationService;
import me.kvdpxne.covilo.application.IUserLifecycleService;
import me.kvdpxne.covilo.application.exception.InvalidPasswordException;
import me.kvdpxne.covilo.application.exception.TokenExpiredException;
import me.kvdpxne.covilo.application.exception.TokenSignatureException;
import me.kvdpxne.covilo.application.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.application.payload.LoginRequest;
import me.kvdpxne.covilo.application.payload.SignupRequest;
import me.kvdpxne.covilo.domain.model.Role;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public final class UserAuthenticationService
  implements IUserAuthenticationService {

  //
  private final IUserLifecycleService userLifecycleUseCase;
  private final ITokenService tokenLifecycleUserCase;
  //
  private final ITokenRepository tokenRepository;

  private final AuthenticationManager authenticationManager;

  @Override
  public Token createAuthentication(
    final SignupRequest request
  ) throws UserAlreadyExistsException, InvalidPasswordException {
    final var email = request.email();
    // Checks whether the provided email address is correct and whether another
    // user has this address assigned to their account.
    if (this.userLifecycleUseCase.checkUserExistsByEmail(email)) {
      UserAlreadyExistsException.byEmail(email);
    }

    final var password = request.password();
    // Checks whether the confirmed password is the same as the original
    // password.
    if (!password.equals(request.confirmPassword())) {
      throw new InvalidPasswordException();
    }

    //
    User user = new User.Builder()
      .email(email)
      .password(password)
      .role(Role.USER)
      .firstName(request.firstName())
      .lastName(request.lastName())
      .gender(request.gender())
      .birthDate(request.birthDate())
      .build();

    //
    user = this.userLifecycleUseCase.createUser(user);

    //
    Token token = new Token.Builder()
      .token(this.tokenLifecycleUserCase.createCompactAccessToken(user))
      .tokenType(TokenType.BEARER)
      .user(user)
      .revoked(false)
      .expired(false)
      .build();

    //
    token = this.tokenRepository.insertToken(token);

    logger.atDebug()
      .setMessage("{} {}")
      .addArgument(user)
      .addArgument(token)
      .log();

    return token;
  }

  @Override
  public Token authenticate(
    final LoginRequest request
  ) throws UserNotFoundException {
    final var email = request.email();
    // Checks whether the provided email address is correct and whether another
    // user has this address assigned to their account.
    if (!this.userLifecycleUseCase.checkUserExistsByEmail(email)) {
      UserNotFoundException.byEmail(email);
    }

    this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        email,
        request.password()
      )
    );

    //
    final var user = this.userLifecycleUseCase.getUserByEmail(email);

    //
    Token token = new Token.Builder()
      .token(this.tokenLifecycleUserCase.createCompactAccessToken(user))
      .tokenType(TokenType.BEARER)
      .user(user)
      .revoked(false)
      .expired(false)
      .build();

    //
    this.tokenRepository.deleteAllTokensByUser(user);
    token = this.tokenRepository.insertToken(token);

    return token;
  }

  @Override
  public Token refreshAuthentication(
    final String compactToken
  ) throws UserNotFoundException, TokenExpiredException, TokenSignatureException {

    final var email = this.tokenLifecycleUserCase.extractSubject(compactToken);

    if (null == email) {
      throw new UserNotFoundException("");
    }

    final var user = this.userLifecycleUseCase.getUserByEmail(email);
    if (email.equals(user.email())) {
      var token = new Token.Builder()
        .token(this.tokenLifecycleUserCase.createCompactAccessToken(user))
        .tokenType(TokenType.BEARER)
        .user(user)
        .revoked(false)
        .expired(false)
        .build();

      //
      this.tokenRepository.deleteAllTokensByUser(user);
      token = this.tokenRepository.insertToken(token);
      return token;
    }

    throw new TokenExpiredException();
  }
}
