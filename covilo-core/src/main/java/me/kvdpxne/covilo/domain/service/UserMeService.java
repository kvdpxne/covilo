package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.port.out.UserMeServicePort;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.common.exceptions.UserPasswordsNotMatchException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.port.out.UserPasswordMatchesPort;
import me.kvdpxne.covilo.shared.Validation;

@RequiredArgsConstructor
public final class UserMeService
  implements UserMeServicePort {

  private final UserServicePort userService;
  private final UserPasswordMatchesPort userPasswordMatches;

  private void checkPassword(
    final User me,
    final String rawPassword
  ) {
    Validation.check(me);
    Validation.empty(rawPassword);

    final var currentPassword = me.password();
    if (!this.userPasswordMatches.matches(rawPassword, currentPassword)) {
      throw new UserPasswordsNotMatchException(
        "The given password does not match the user's password."
      );
    }
  }

  @Override
  public void updateLastModifiedDate(
    final User me
  ) {
    this.userService.updateLastModifiedDate(me);
  }

  /**
   * Updates the email address for a specific user but validates the entered
   * data for the currently authenticated user.
   *
   * @see UserServicePort#updateUserEmail(User, String)
   */
  @Override
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
  @Override
  public void updateMePassword(
    final User me,
    final String newPassword,
    final String confirmedPassword,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);

    if (null == newPassword || null == confirmedPassword) {
      throw new UserInvalidPasswordException(
        ""
      );
    }

    if (!newPassword.equals(confirmedPassword)) {
      throw new UserInvalidPasswordException();
    }

    this.userService.updateUserPassword(me, newPassword);
  }

  @Override
  public void deleteMe(
    final User me,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);
    this.userService.deleteUser(me);
  }
}
