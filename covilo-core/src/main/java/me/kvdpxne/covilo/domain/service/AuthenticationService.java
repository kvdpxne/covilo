package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.exceptions.InvalidEmailAddressException;
import me.kvdpxne.covilo.domain.exceptions.InvalidPasswordException;
import me.kvdpxne.covilo.domain.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.TokenType;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.model.TokenPair;
import me.kvdpxne.covilo.infrastructure.security.jwt.JjwtServiceExtension;
import me.kvdpxne.covilo.shared.Validation;

/**
 * Service class responsible for authentication-related operations such as user
 * login, signup, and token generation.
 */
@Slf4j
@RequiredArgsConstructor
public final class AuthenticationService {

  /**
   * The {@link UserService} instance used for user-related operations.
   */
  private final UserService userService;

  /**
   * The {@link PasswordAuthenticator} instance used for authenticating user
   * passwords.
   */
  private final PasswordAuthenticator passwordAuthenticator;

  /**
   * The {@link JjwtServiceExtension} instance used for generating JWT tokens.
   */
  private final JjwtServiceExtension jwtService;

  /**
   * Signs up a new user and returns a pair of access and refresh tokens.
   *
   * @param user The user information to be registered.
   * @return A pair of access and refresh tokens for the newly registered user.
   * @throws NullPointerException         If the provided user object is null.
   * @throws IllegalArgumentException     If the provided user object is invalid
   *                                      or incomplete.
   * @throws InvalidEmailAddressException If the provided email address does not
   *                                      meet the required format
   * @throws InvalidPasswordException     If the provided password does not meet
   *                                      the required security standards.
   * @throws UserAlreadyExistsException   If a user with the same email or
   *                                      identifier already exists.
   */
  public TokenPair signup(
    final User user
  ) {
    // Create the user in the system
    final var createdUser = this.userService.createUser(user);

    // Generate access and refresh tokens for the newly created user
    final var tokenPair = this.jwtService.buildTokenPair(createdUser);

    // Log user creation and token generation
    logger.atDebug()
      .setMessage("Token pair {} has been created for user {}.")
      .addArgument(tokenPair)
      .addArgument(createdUser)
      .log();

    // Return the generated token pair
    return tokenPair;
  }

  /**
   * Logs in a user with the provided name (email) and password, and returns a
   * pair of access and refresh tokens.
   *
   * @param name     The email address of the user attempting to log in.
   * @param password The password of the user attempting to log in.
   * @return A pair of access and refresh tokens for the logged-in user.
   * @throws NullPointerException     If either the name or password is null.
   * @throws IllegalArgumentException If the name is an empty string or contains
   *                                  only whitespaces.
   * @throws UserNotFoundException    If no user with the provided email address
   *                                  is found in the system.
   */
  public TokenPair login(
    final String name,
    final String password
  ) {
    // Get the user with the provided email address
    final var user = this.userService.getUserByEmail(name);

    // Authenticate the user's password
    this.passwordAuthenticator.authenticate(name, password);

    // Generate and return a pair of access and refresh tokens
    // for the logged-in user
    return this.jwtService.buildTokenPair(user);
  }

  /**
   * Generates a new access token using the provided refresh token.
   *
   * @param refreshToken The refresh token used to generate the new access
   *                     token.
   * @return A new pair of access and refresh tokens.
   * @throws NullPointerException     If the provided refresh token is null.
   * @throws IllegalArgumentException If the provided refresh token does not
   *                                  contain information to identify the user.
   * @throws UserNotFoundException    If no user can be identified with the
   *                                  information in the refresh token.
   */
  public TokenPair refreshAccessToken(
    final String refreshToken
  ) {
    return TokenPair.builder()
      .withAccessToken(
        this.jwtService.createAccessJws(
          this.userService.getUserByIdentifier(
            UUID.fromString(
              Validation.check(
                this.jwtService.readJws(refreshToken).getSubject(),
                () -> "The token does not contain information that can be " +
                  "used to identify the user."
              )
            )
          )
        )
      )
      .withRefreshToken(refreshToken)
      .withExpiry(this.jwtService.getAccessTokenExpiryMoment())
      .withTokenType(TokenType.BEARER)
      .build();
  }

  /**
   *
   */
  public TokenPair refreshAccessToken(
    final TokenPair tokenPair
  ) {
    return tokenPair.toBuilder()
      .withAccessToken(
        this.jwtService.createAccessJws(
          this.userService.getUserByIdentifier(
            UUID.fromString(
              Validation.check(
                this.jwtService.readJws(tokenPair.getRefreshToken()).getSubject(),
                () -> "The token does not contain information that can be " +
                  "used to identify the user."
              )
            )
          )
        )
      )
      .withExpiry(this.jwtService.getAccessTokenExpiryMoment())
      .build();
  }
}
