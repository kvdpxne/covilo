package me.kvdpxne.covilo.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.port.out.UserPasswordEncodePort;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.domain.port.out.UserValidatorPort;
import me.kvdpxne.covilo.shared.Validation;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserService
  implements UserServicePort {

  /**
   * Repository for accessing user data.
   */
  private final UserRepository userRepository;

  /**
   * Port for encoding user passwords.
   */
  private final UserPasswordEncodePort userPasswordEncoder;

  /**
   * Port for validating user data.
   */
  private final UserValidatorPort userValidator;

  /**
   * For full method documentation, refer to the
   * {@link UserServicePort#getUserByIdentifier(UUID)} method.
   */
  @Override
  public User getUserByIdentifier(
    final UUID identifier
  ) {
    // Validate the provided identifier.
    Validation.check(identifier);

    // Retrieve the user from the repository.
    final var user = this.userRepository.findUserByIdentifierOrNull(identifier);

    // Throw UserNotFoundException if no user is found.
    if (null == user) {
      throw UserNotFoundException.byIdentifier(identifier);
    }

    // Return the user.
    return user;
  }

  /**
   * For full method documentation, refer to the
   * {@link UserServicePort#getUserByEmail(String)}} method.
   */
  @Override
  public User getUserByEmail(
    final String email
  ) throws UserNotFoundException {
    this.userValidator.checkEmail(email);
    final var user = this.userRepository.findUserByEmailOrNull(email);
    if (null == user) {
      UserNotFoundException.byEmail(email);
    }
    return user;
  }

  /**
   * Checks if a user with the same identifier already exists.
   *
   * @param user The user object to check the identifier for.
   * @throws UserAlreadyExistsException If a user with the same identifier
   *                                    already exists.
   */
  private void checkExistsUserByIdentifier(
    final User user
  ) throws UserAlreadyExistsException {
    // Extract the identifier from the provided user object.
    final var identifier = user.identifier();

    // Check if a user with the same identifier exists in the repository and
    // throw a UserAlreadyExistsException if a user with the same identifier
    // already exists.
    if (this.userRepository.existsUserByIdentifier(identifier)) {
      UserAlreadyExistsException.byIdentifier(identifier);
    }
  }

  /**
   * Checks if a user with the same email already exists.
   *
   * @param user The user object to check the email for.
   * @throws UserAlreadyExistsException If a user with the same email already
   *                                    exists.
   */
  private void checkExistsUserByEmail(
    final User user
  ) throws UserAlreadyExistsException {
    // Extract the email from the provided user object.
    final var email = user.email();

    // Check if a user with the same email exists in the repository and throw
    // a UserAlreadyExistsException if a user with the same email already
    // exists.
    if (this.checkUserExistsByEmail(email)) {
      throw UserAlreadyExistsException.byEmail(email);
    }
  }

  /**
   * Creates a new user based on the provided user data.
   *
   * @param source The user data to create the user from.
   * @return The newly created user.
   * @throws UserAlreadyExistsException If a user with the same identifier
   *                                    or email already exists.
   */
  @Override
  public User createUser(
    final User source
  ) throws UserAlreadyExistsException {
    // Check if a user with the same identifier or email already exists.
    this.checkExistsUserByIdentifier(source);
    this.checkExistsUserByEmail(source);

    // Encode the password before storing it.
    final var encodedPassword = this.userPasswordEncoder.encode(
      source.password()
    );

    // Create a builder based on the provided user data.
    var userBuilder = source.toBuilder();

    // Assign a new UUID as the identifier if the user is new.
    if (source.isNew()) {
      userBuilder.identifier(UUID.randomUUID());
    }

    // Set the creation date to the current date and time if it was not
    // previously set.
    if (!source.wasCreated()) {
      if (!source.wasModified()) {
        throw new IllegalArgumentException(
          "Invalid user state: Neither created nor modified."
        );
      }
      userBuilder.createdDate(LocalDateTime.now());
    }

    // Build the user object with the updated builder, including the
    // encoded password.
    var user = userBuilder
      .password(encodedPassword)
      .build();

    try {
      // Insert the new user into the repository.
      user = this.userRepository.insert(user);
    } catch (final Throwable throwable) {
      // Wrap and rethrow any exceptions occurred during user insertion.
      throw new RuntimeException(
        "An unhandled exception occurred while inserting a user.",
        throwable
      );
    }

    // Log the creation of the user.
    logger.atDebug()
      .setMessage("Created user: {}")
      .addArgument(user)
      .log();

    return user;
  }

  @Override
  public void updateLastModifiedDate(
    final User user
  ) {
    Validation.check(user);
    if (this.userRepository.updateLastModifiedDateByIdentifier(
      user.identifier()
    )) {

    }
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
    this.userValidator.checkEmail(newEmail);

    //
    final var email = user.email();

    if (email.equals(newEmail)) {
      return;
    }

    try {
      this.userRepository.updateEmailByIdentifier(
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
    this.userValidator.checkPassword(newPassword);
    final var password = user.password();
    if (password.equals(newPassword)) {
      return;
    }
    final var encodedPassword = this.userPasswordEncoder.encode(newPassword);
    this.userRepository.updatePasswordByIdentifier(user.identifier(), encodedPassword);
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
    this.userValidator.checkEmail(email);
    return this.userRepository.existsUserByEmail(email);
  }
}
