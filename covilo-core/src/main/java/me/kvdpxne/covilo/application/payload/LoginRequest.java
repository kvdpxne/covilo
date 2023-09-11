package me.kvdpxne.covilo.application.payload;

public record LoginRequest(
  String email,
  String password
) { }
