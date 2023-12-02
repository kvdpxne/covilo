package me.kvdpxne.covilo.application.dto;

public record TokenDto(
  String compactAccessToken,

  String compactRefreshToken
) { }
