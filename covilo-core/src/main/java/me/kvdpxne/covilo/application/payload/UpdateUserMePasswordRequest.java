package me.kvdpxne.covilo.application.payload;

/**
 * Body of a password update request by the currently authenticated user.
 *
 * @param currentPassword    The current password of the currently
 *                           authenticated user.
 * @param newPassword        A new password.
 * @param confirmedPassword Repeated and identical new password.
 */
public record UpdateUserMePasswordRequest(
  String currentPassword,
  String newPassword,
  String confirmedPassword
) {}
