package me.kvdpxne.covilo.presentation.dto;

import java.util.UUID;

public record CategoryDto(
  UUID identifier,
  String name
) { }
