package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.PasswordEncodingUseCase;
import me.kvdpxne.covilo.application.UserLifecycleUseCase;
import me.kvdpxne.covilo.domain.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserLifecycleService implements UserLifecycleUseCase {

  private final UserRepository userRepository;
  private final PasswordEncodingUseCase passwordEncodingUseCase;

  /**
   * @throws UserNotFoundException If the user with the given identifier could
   *                               not be found.
   */
  @Override
  public User getUserByIdentifier(
    final UUID identifier
  ) throws UserNotFoundException {
    final User user = this.userRepository.findUserByIdentifierOrNull(identifier);
    if (null == user) {
      throw new UserNotFoundException(
        "User with identifier \"%s\" does not exist.",
        identifier
      );
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
    final User user = this.userRepository.findUserByEmailOrNull(email);
    if (null == user) {
      throw new UserNotFoundException(
        "User with email address \"%s\" does not exist.",
        email
      );
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
    final UUID identifier = user.identifier();
    if (this.userRepository.existsUserByIdentifier(identifier)) {
      throw new UserAlreadyExistsException(
        "User with identifier \"%s\" already exists.",
        identifier
      );
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
    final String email = user.email();
    if (this.userRepository.existsUserByEmail(email)) {
      throw new UserAlreadyExistsException(
        "User with email \"%s\" already exists.",
        email
      );
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

    final String encodedPassword = this.passwordEncodingUseCase.encode(
      source.password()
    );

    User user = source.toBuilder()
      .password(encodedPassword)
      .build();

    user = this.userRepository.insertUser(user);
    logger.info("Created user: {}", user);

    return user;
  }
}
