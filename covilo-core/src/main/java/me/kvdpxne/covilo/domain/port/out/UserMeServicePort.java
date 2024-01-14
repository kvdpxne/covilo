package me.kvdpxne.covilo.domain.port.out;

import me.kvdpxne.covilo.domain.model.User;

public interface UserMeServicePort {

  /**
   * Updates the email address for a specific user but validates the entered
   * data for the currently authenticated user.
   *
   * @see UserServicePort#updateUserEmail(User, String)
   */
  void updateMeEmail(
    final User me,
    final String newEmail,
    final String currentPassword
  );


  /**
   * Updates the password for a specific user but validates the entered data
   * for the currently authenticated user.
   *
   * @see UserServicePort#updateUserPassword(User, String)
   */
  void updateMePassword(
    final User me,
    final String newPassword,
    final String confirmedPassword,
    final String currentPassword
  );
}
