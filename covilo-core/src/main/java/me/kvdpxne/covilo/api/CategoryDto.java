package me.kvdpxne.covilo.api;

import java.util.UUID;

public record CategoryDto(
  UUID identifier,
  String name
) { }
