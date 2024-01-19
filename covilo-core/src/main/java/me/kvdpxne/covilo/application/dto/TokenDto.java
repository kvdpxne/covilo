package me.kvdpxne.covilo.application.dto;

@Deprecated
public record TokenDto(
  String compactAccessToken,

  String compactRefreshToken
) { }
