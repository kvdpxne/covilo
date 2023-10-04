package me.kvdpxne.covilo.application.payload;

public record UpdatePasswordRequest(
  String currentPassword,
  String newPassword,
  String confirmNewPassword
) { }
