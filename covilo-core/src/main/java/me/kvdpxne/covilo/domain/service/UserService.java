package me.kvdpxne.covilo.domain.service;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.exceptions.InvalidEmailAddressException;
import me.kvdpxne.covilo.domain.exceptions.InvalidPasswordException;
import me.kvdpxne.covilo.domain.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.uid.Uid;
import me.kvdpxne.covilo.shared.Validation;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserService {

  /**
   * Repository for accessing user data.
   */
  final UserRepository userRepository;

  /**
   * Port for encoding user passwords.
   */
  final PasswordEncodingService passwordEncodingService;

  /**
   * Counts the total number of users in the repository.
   *
   * @return The total number of users in the repository, as a non-negative
   *         long value.
   */
  public long countUsers() {
    return Math.absExact(
      this.userRepository.countUsers()
    );
  }

  /**
   * Checks if a user with the specified identifier exists in the repository.
   *
   * @param identifier The identifier of the user to check.
   * @return {@code true} if a user with the specified identifier exists,
   *         otherwise {@code false}.
   */
  public boolean _checkUserExistsByIdentifier(
    final String identifier
  ) {
    return this.userRepository.existsUserByIdentifier(identifier);
  }

  /**
   * Validates the provided user identifier, ensuring it is not null.
   * If the identifier is null, a {@link NullPointerException} is thrown.
   *
   * @param identifier The user identifier to validate.
   * @return The validated user identifier.
   * @throws NullPointerException If the provided user identifier is {@code null}.
   */
  private String validUserIdentifier(
    final String identifier
  ) {
    return Validation.check(
      identifier,
      () -> "The provided user identifier cannot be null."
    );
  }

  /**
   * Validates the provided user object, ensuring it is not null.
   * If the user object is null, a {@link NullPointerException} is thrown.
   *
   * @param user The user object to validate.
   * @return The validated user object.
   * @throws NullPointerException If the provided user object is {@code null}.
   */
  private User validUser(
    final User user
  ) {
    return Validation.check(
      user,
      () -> "The provided user cannot be null."
    );
  }

  /**
   * Checks if a user with the specified identifier exists in the repository.
   *
   * @param identifier The identifier of the user to check.
   * @return {@code true} if a user with the specified identifier exists, otherwise {@code false}.
   * @throws NullPointerException If the provided user identifier is {@code null}.
   */
  public boolean checkUserExistsByIdentifier(
    final String identifier
  ) {
    return this._checkUserExistsByIdentifier(
      this.validUserIdentifier(identifier)
    );
  }

  public boolean checkUserExistsByEmail(final String email) {
    this.checkEmail(email);
    return this.userRepository.existsUserByEmail(email);
  }

  /**
   * Checks if a user exists in the repository based on the provided user object.
   *
   * @param user The user object to check.
   * @return {@code true} if the user exists, otherwise {@code false}.
   * @throws NullPointerException If the provided user object is {@code null}.
   */
  public boolean checkUserExists(
    final User user
  ) {
    return this._checkUserExistsByIdentifier(
      this.validUser(user).getIdentifier()
    );
  }

  /**
   * Retrieves a page of users.
   *
   * @param pageable The pagination information.
   * @return A page containing users.
   */
  public Page<User> getUsers(
    final Pageable pageable
  ) {
    return this.userRepository.findUsers(
      pageable
    );
  }

  /**
   *
   */
  public User _getUserByIdentifier(
    final String identifier
  ) {
    return this.userRepository
      .findUserByIdentifier(identifier)
      .orElseThrow(() -> new UserNotFoundException(
        "The provided user identifier does not exist."
      ));
  }

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param identifier The unique identifier of the user.
   * @return The user with the specified identifier.
   * @throws NullPointerException  If the identifier is null.
   * @throws UserNotFoundException If no user is found with the specified
   *                               identifier.
   */
  public User getUserByIdentifier(
    final String identifier
  ) {
    return this._getUserByIdentifier(
      this.validUserIdentifier(identifier)
    );
  }

  /**
   * Regular expression pattern for validating Google Gmail email addresses.
   *
   * <p>
   * The pattern ensures that the email address conforms to standard Gmail
   * email format.
   * </p>
   *
   * <p>
   * Note: This pattern is specifically designed for validating Google Gmail
   * email addresses.
   * </p>
   */
  public static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9+_-]+" +
    "(\\.[A-Za-z0-9+_-]+)*@[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*" +
    "(\\.[A-Za-z]{2,})$";

  /**
   * Regular expression pattern for validating passwords.
   *
   * <p>
   * The pattern ensures that the password meets the following criteria:
   * </p>
   * <ul>
   *   <li>Contains at least one digit</li>
   *   <li>Contains at least one lowercase letter</li>
   *   <li>Contains at least one uppercase letter</li>
   *   <li>Consists of at least 8 characters</li>
   * </ul>
   */
  public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*" +
    "[A-Z])(?=.*[a-zA-Z]).{8,}$";

  /**
   * Checks whether the provided email address is correct and meets email
   * address standards.
   */
  public void checkEmail(
    final String email
  ) {
    // Checks whether the given string is literally empty.
    Validation.empty(email);

    // Checks whether the email address complies with the standard.
    if (!email.matches(EMAIL_PATTERN)) {
      throw new InvalidEmailAddressException(
        "The given email address is invalid."
      );
    }
  }

  /**
   * Checks whether the entered password is correct and meets password
   * standards.
   */
  public void checkPassword(
    final String password
  ) {
    // Checks whether the given string is literally empty.
    Validation.empty(password);

    // Checks whether the password complies with the standard.
    if (!password.matches(PASSWORD_PATTERN)) {
      throw new InvalidPasswordException(
        "The given password is invalid."
      );
    }
  }

  /**
   * Retrieves a user by their email address.
   *
   * <p>
   * Note: Using the {@link #getUserByIdentifier(String)} method with the user's
   * unique identifier will always be more efficient than using this method,
   * as it directly accesses the user's record based on the identifier.
   * </p>
   *
   * @param email The email address of the user.
   * @return The user with the specified email address.
   * @throws NullPointerException             If the email address is null.
   * @throws IllegalArgumentException         If the email address is empty or
   *                                          consists only of whitespace.
   * @throws InvalidEmailAddressException If the email address provided is
   *                                          not valid.
   * @throws UserNotFoundException            If no user is found with the
   *                                          specified email address.
   */
  public User getUserByEmail(
    final String email
  ) throws UserNotFoundException {
    //
    this.checkEmail(email);

    //
    return this.userRepository.findUserByEmail(email)
      .orElseThrow(() -> new UserNotFoundException(
        STR."No found user with given \{email} email adress."
      ));
  }

  private User _createUser(
    final User user
  ) {
    final var newUser = this.userRepository
      .insertUserAndReturn(user);

    assert null != user
      : "";

    logger.atInfo()
      .log("");

    return newUser;
  }

  /**
   * Creates a new user based on the provided user data.
   *
   * @param user The user data to create the user from.
   * @return The newly created user.
   * @throws UserAlreadyExistsException If a user with the same identifier
   *                                    or email already exists.
   */
  public User createUser(
    final User user
  ) {
    // Check if a user with the same identifier or email already exists.
    this.validUser(user);

    this.checkEmail(user.getName());
    this.checkPassword(user.getPassword());

    var builder = User.builder();

    if (user.isNew()) {
      final var randomIdentifier = Uid.next();

      builder.withIdentifier(randomIdentifier);

      logger.atDebug()
        .setMessage("Assin")
        .addArgument(randomIdentifier)
        .log();
    } else {
      final var identifier = user.getIdentifier();

      if (this._checkUserExistsByIdentifier(identifier)) {
        throw new UserAlreadyExistsException(
          ""
        );
      }

       builder.withIdentifier(identifier);
    }

    return this._createUser(
      builder.withEmail(user.getLowerName())
        .withPassword(this.passwordEncodingService.encode(user.getPassword()))
        .withFirstName(user.getFirstName())
        .withLastName(user.getLastName())
        .withGender(user.getGender())
        .withBrithDate(user.getBirthDate())
        .withCreatedDate()
        .build()
    );
  }

  private void fs(final User user) {
    Validation.check(
      user.isNew(),
      () -> "Cannot update a newly created user object."
    );
  }

  public void updateUser(
    final User user
  ) {
    this.validUser(user);

    this.fs(user);
  }

  /**
   * Updates the email address for the specified user.
   */
  public void updateUserEmail(
    final User user,
    final String newEmail
  ) {
    //
    this.validUser(user);
    this.fs(user);

    //
    this.checkEmail(newEmail);

    //
    this.userRepository.updateUser(
      user.toBuilder()
        .withEmail(newEmail.toLowerCase(Locale.ENGLISH))
        .withLastModifiedDate()
        .build()
    );

    //
    logger.atInfo()
      .setMessage("{}")
      .addArgument(user)
      .log();
  }

  /**
   * Updates the password for the specified user.
   */
  public void updateUserPassword(
    final User user,
    final String newPassword
  ) {
    //
    this.validUser(user);
    this.fs(user);

    //
    this.checkPassword(newPassword);

    logger.atTrace()
      .setMessage("Preparing to change the password for a user: {}.")
      .addArgument(user)
      .log();

    //
    this.userRepository.updateUser(
      user.toBuilder()
        .withPassword(this.passwordEncodingService.encode(newPassword))
        .withLastModifiedDate()
        .build()
    );

    //
    logger.atInfo()
      .setMessage("Password changed for user: {}")
      .addArgument(user)
      .log();
  }

  public void deleteUserByIdentifier(
    final String identifier
  ) {

    if (this.userRepository.existsUserByIdentifier(identifier)) {
      throw new UserNotFoundException("");
    }

    this.userRepository.deleteUserByIdentifier(identifier);

    logger.atDebug()
      .setMessage("Deleted user: {}")
      .addArgument(identifier)
      .log();
  }

  public void deleteUser(
    final User user
  ) {
    Validation.check(user);
    this.deleteUserByIdentifier(user.getIdentifier());
  }
}
