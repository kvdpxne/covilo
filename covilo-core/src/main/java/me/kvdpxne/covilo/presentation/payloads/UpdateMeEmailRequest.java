package me.kvdpxne.covilo.presentation.payloads;

/**
 * Body of a request to update the email address by the currently authenticated
 * user.
 *
 * @param currentPassword The current password of the currently authenticated
 *                        user.
 * @param newEmail        A new email address.
 */
public record UpdateMeEmailRequest(
  String currentPassword,
  String newEmail
) {}
