package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record CategoryDto(
  UUID identifier,
  String name
) { }
