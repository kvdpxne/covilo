package me.kvdpxne.covilo.presentation.dto;

@Deprecated
public record TokenDto(
  String compactAccessToken,

  String compactRefreshToken
) { }
