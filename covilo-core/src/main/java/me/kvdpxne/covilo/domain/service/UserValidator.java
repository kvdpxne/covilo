package me.kvdpxne.covilo.domain.service;

import me.kvdpxne.covilo.domain.port.out.UserValidatorPort;
import me.kvdpxne.covilo.common.exceptions.UserInvalidEmailAddressException;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.shared.Validation;

public final class UserValidator
  implements UserValidatorPort {

  /**
   *
   */
  public static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9+_-]+" +
    "(\\.[A-Za-z0-9+_-]+)*@[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*" +
    "(\\.[A-Za-z]{2,})$";

  /**
   *
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
