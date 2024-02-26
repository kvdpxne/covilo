package me.kvdpxne.covilo.presentation.payloads;

public record LoginRequest(
  String email,
  String password
) { }
