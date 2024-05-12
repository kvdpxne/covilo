package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.exceptions.InvalidPasswordException;
import me.kvdpxne.covilo.domain.exceptions.PasswordMismatchException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.shared.Validation;

@RequiredArgsConstructor
public final class MeService {

  private final UserService userService;

  private void checkPassword(
    final User me,
    final String rawPassword
  ) {
    Validation.check(me);
    Validation.empty(rawPassword);

    final var currentPassword = me.getPassword();
    if (!this.userService.passwordEncodingService.matches(rawPassword, currentPassword)) {
      throw new PasswordMismatchException(
        "The given password does not match the user's password."
      );
    }
  }

  /**
   * Updates the email address for a specific user but validates the entered
   * data for the currently authenticated user.
   *
   * @see UserServicePort#updateUserEmail(User, String)
   */
  public void updateMeEmail(
    final User me,
    final String newEmail,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);
    this.userService.updateUserEmail(me, newEmail);
  }

  /**
   * Updates the password for a specific user but validates the entered data
   * for the currently authenticated user.
   *
   * @see UserServicePort#updateUserPassword(User, String)
   */
  public void updateMePassword(
    final User me,
    final String newPassword,
    final String confirmedPassword,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);

    if (null == newPassword || null == confirmedPassword) {
      throw new InvalidPasswordException(
        "New password and password confirmation cannot be null."
      );
    }

    if (!newPassword.equals(confirmedPassword)) {
      throw new InvalidPasswordException(
        "The new password must be the same as the password confirmation."
      );
    }

    this.userService.updateUserPassword(me, newPassword);
  }

  public void deleteMe(
    final User me,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);
    this.userService.deleteUser(me);
  }
}
