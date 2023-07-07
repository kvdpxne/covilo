package me.kvdpxne.covilo.api.request;

public record LoginCredentials(
  String recognizableName,
  String password
) { }
