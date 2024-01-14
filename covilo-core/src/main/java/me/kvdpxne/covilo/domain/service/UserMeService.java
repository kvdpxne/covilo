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

  private final UserServicePort userServicePort;
  private final UserPasswordMatchesPort userPasswordMatchesPort;

  private void checkPassword(
    final User me,
    final String rawPassword
  ) {
    Validation.check(me);
    Validation.empty(rawPassword);

    final var currentPassword = me.password();
    if (!this.userPasswordMatchesPort.matches(rawPassword, currentPassword)) {
      throw new UserPasswordsNotMatchException(
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
  @Override
  public void updateMeEmail(
    final User me,
    final String newEmail,
    final String currentPassword
  ) {
    this.checkPassword(me, currentPassword);
    this.userServicePort.updateUserEmail(me, newEmail);
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

    this.userServicePort.updateUserPassword(me, newPassword);
  }
}
