package me.kvdpxne.covilo.presentation.payloads;

/**
 * Body of a password update request by the currently authenticated user.
 *
 * @param currentPassword    The current password of the currently
 *                           authenticated user.
 * @param newPassword        A new password.
 * @param confirmedPassword Repeated and identical new password.
 */
public record UpdateMePasswordRequest(
  String currentPassword,
  String newPassword,
  String confirmedPassword
) {}
