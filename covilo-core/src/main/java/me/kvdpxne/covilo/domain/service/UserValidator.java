package me.kvdpxne.covilo.domain.service;

import me.kvdpxne.covilo.common.exceptions.UserInvalidEmailAddressException;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.shared.Validation;

public final class UserValidator {

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
  @Override
  public void checkEmail(
    final String email
  ) {
    // Checks whether the given string is literally empty.
    Validation.empty(email);

    // Checks whether the email address complies with the standard.
    if (!email.matches(EMAIL_PATTERN)) {
      throw new UserInvalidEmailAddressException(
        "The given email address is invalid."
      );
    }
  }

  /**
   * Checks whether the entered password is correct and meets password
   * standards.
   */
  @Override
  public void checkPassword(
    final String password
  ) {
    // Checks whether the given string is literally empty.
    Validation.empty(password);

    // Checks whether the password complies with the standard.
    if (!password.matches(PASSWORD_PATTERN)) {
      throw new UserInvalidPasswordException(
        "The given password is invalid."
      );
    }
  }
}
