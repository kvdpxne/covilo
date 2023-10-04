package me.kvdpxne.covilo.application.payload;

public record UpdateEmailRequest(
  String currentPassword,
  String newEmail
) {
}
