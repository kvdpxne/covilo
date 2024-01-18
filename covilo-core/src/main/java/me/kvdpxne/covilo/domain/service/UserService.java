package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.domain.port.out.UserValidatorPort;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.port.out.UserPasswordEncodePort;
import me.kvdpxne.covilo.shared.Validation;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserService
  implements UserServicePort {

  private final UserRepository userRepository;
  private final UserPasswordEncodePort userPasswordEncodePort;

  /**
   *
   */
  private final UserValidatorPort userValidatorPort;

  /**
   * @throws UserNotFoundException If the user with the given identifier could
   *                               not be found.
   */
  @Override
  public User getUserByIdentifier(
    final UUID identifier
  ) throws UserNotFoundException {
    final var user = this.userRepository.findUserByIdentifierOrNull(identifier);
    if (null == user) {
      UserNotFoundException.byIdentifier(identifier);
    }
    return user;
  }

  /**
   * @throws UserNotFoundException If the user with the given email address
   *                               could not be found.
   */
  @Override
  public User getUserByEmail(
    final String email
  ) throws UserNotFoundException {
    final var user = this.userRepository.findUserByEmailOrNull(email);
    if (null == user) {
      UserNotFoundException.byEmail(email);
    }
    return user;
  }

  /**
   * @throws UserAlreadyExistsException If the specified user has an identifier
   *                                    that is already assigned to another
   *                                    user.
   */
  private void checkExistsUserByIdentifier(
    final User user
  ) throws UserAlreadyExistsException {
    final var identifier = user.identifier();
    if (this.userRepository.existsUserByIdentifier(identifier)) {
      UserAlreadyExistsException.byIdentifier(identifier);
    }
  }

  /**
   * @throws UserAlreadyExistsException If the specified user has an email
   *                                    address that is already assigned to
   *                                    another user.
   */
  private void checkExistsUserByEmail(
    final User user
  ) throws UserAlreadyExistsException {
    final var email = user.email();
    // Checks whether the email address is correct and whether another user has
    // already been assigned this email address.
    if (this.checkUserExistsByEmail(email)) {
      throw UserAlreadyExistsException.byEmail(email);
    }
  }

  /**
   * @throws UserAlreadyExistsException If the user's identifier or email
   *                                    address is already assigned to another
   *                                    user.
   */
  @Override
  public User createUser(
    final User source
  ) throws UserAlreadyExistsException {
    // Checks whether the identifier and email address of the given user are
    // not already assigned to another user because the identifier and email
    // address should be unique.
    this.checkExistsUserByIdentifier(source);
    this.checkExistsUserByEmail(source);

    // Encodes the raw currentPassword according to the given rules defined in the
    // application infrastructure.
    final var encodedPassword = this.userPasswordEncodePort.encode(
      source.password()
    );

    var user = source.toBuilder()
      .password(encodedPassword)
      .build();

    try {
      user = this.userRepository.insertUser(user);
    } catch (final Throwable throwable) {
      throw new RuntimeException(
        "An unhandled exception occurred while inserting a user.",
        throwable
      );
    }

    logger.atDebug()
      .setMessage("Created user: {}")
      .addArgument(user)
      .log();

    return user;
  }

  /**
   * Updates the email address for the specified user.
   */
  @Override
  public void updateUserEmail(
    final User user,
    final String newEmail
  ) {
    Validation.check(user);
    this.userValidatorPort.checkEmail(newEmail);

    //
    final var email = user.email();

    if (email.equals(newEmail)) {
      return;
    }

    try {
      this.userRepository.updateUserEmailByIdentifier(
        user.identifier(),
        newEmail
      );
    } catch (final Throwable reason) {
      logger.error("Unhandled reason: ", reason.getCause());
      return;
    }

    logger.atTrace()
      .setMessage("{}")
      .addArgument(user)
      .log();
  }

  /**
   * Updates the password for the specified user.
   */
  @Override
  public void updateUserPassword(
    final User user,
    final String newPassword
  ) {
    Validation.check(user);
    this.userValidatorPort.checkPassword(newPassword);
    final var password = user.password();
    if (password.equals(newPassword)) {
      return;
    }
    final var encodedPassword = this.userPasswordEncodePort.encode(newPassword);
    this.userRepository.updateUserPasswordByIdentifier(user.identifier(), encodedPassword);
  }

  @Override
  public void deleteUserByIdentifier(
    final UUID identifier
  ) throws UserNotFoundException {

    if (this.userRepository.existsUserByIdentifier(identifier)) {
      throw UserNotFoundException.byIdentifier(identifier);
    }

    this.userRepository.deleteUserByIdentifier(identifier);

    logger.atDebug()
      .setMessage("Deleted user: {}")
      .addArgument(identifier)
      .log();
  }

  @Override
  public void deleteUser(
    final User user
  ) {
    Validation.check(user);
    this.deleteUserByIdentifier(user.identifier());
  }

  @Override
  public boolean checkUserExistsByEmail(final String email) {
    this.userValidatorPort.checkEmail(email);
    return this.userRepository.existsUserByEmail(email);
  }
}
