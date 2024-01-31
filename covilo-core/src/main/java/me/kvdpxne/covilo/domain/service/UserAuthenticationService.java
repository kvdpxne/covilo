package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.port.out.ITokenService;
import me.kvdpxne.covilo.domain.port.out.UserAuthenticationServicePort;
import me.kvdpxne.covilo.domain.port.out.UserPasswordAuthenticationTokenPort;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.presentation.dto.TokenDto;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.common.exceptions.TokenExpiredException;
import me.kvdpxne.covilo.common.exceptions.TokenSignatureException;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.presentation.payloads.LoginRequest;
import me.kvdpxne.covilo.presentation.payloads.SignupRequest;
import me.kvdpxne.covilo.domain.model.Role;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;

/**
 * Service responsible for the user authentication process.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserAuthenticationService
  implements UserAuthenticationServicePort {

  private final UserServicePort userService;
  private final UserPasswordAuthenticationTokenPort userPasswordAuthenticationToken;

  private final ITokenService tokenLifecycleUserCase;
  //
  private final TokenRepository tokenRepository;

  @Override
  public Token createAuthentication(
    final SignupRequest request
  ) throws UserAlreadyExistsException, UserInvalidPasswordException {
    final var email = request.email();
    // Checks whether the provided email address is correct and whether another
    // user has this address assigned to their account.
    if (this.userService.checkUserExistsByEmail(email)) {
      throw UserAlreadyExistsException.byEmail(email);
    }

    final var password = request.password();
    // Checks whether the confirmed currentPassword is the same as the original
    // currentPassword.
    if (!password.equals(request.confirmPassword())) {
      throw new UserInvalidPasswordException();
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
    user = this.userService.createUser(user);

    //
    Token token = Token.builder()
      .compactToken(this.tokenLifecycleUserCase.createCompactAccessToken(user))
      .tokenType(TokenType.ACCESS)
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
  public TokenDto authenticate(
    final LoginRequest request
  ) throws UserNotFoundException {
    final var email = request.email();
    // Checks whether the provided email address is correct and whether another
    // user has this address assigned to their account.
    if (!this.userService.checkUserExistsByEmail(email)) {
      UserNotFoundException.byEmail(email);
    }

    this.userPasswordAuthenticationToken.authenticate(
      email,
      request.password()
    );

    //
    final var user = this.userService.getUserByEmail(email);

    final var compactRefreshToken = this.tokenLifecycleUserCase
      .createCompactRefreshToken(user);

    final var compactAccessToken = this.tokenLifecycleUserCase
      .createCompactAccessToken(user);

    Token token = Token.builder()
      .compactToken(compactRefreshToken)
      .tokenType(TokenType.REFRESH)
      .user(user)
      .build();

    this.tokenRepository.deleteAllTokensByUser(user);
    this.tokenRepository.insertToken(token);

    return new TokenDto(compactAccessToken, compactRefreshToken);
  }

  @Override
  public Token refreshAuthentication(
    final String compactToken
  ) throws UserNotFoundException, TokenExpiredException, TokenSignatureException {

    final var email = this.tokenLifecycleUserCase.extractSubject(compactToken);

    if (null == email) {
      throw new UserNotFoundException("");
    }

    final var user = this.userService.getUserByEmail(email);
    return Token.builder()
      .compactToken(this.tokenLifecycleUserCase.createCompactAccessToken(user))
      .tokenType(TokenType.ACCESS)
      .user(user)
      .revoked(false)
      .expired(false)
      .build();
  }
}
