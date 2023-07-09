package me.kvdpxne.covilo.api;

import java.util.UUID;

public record CrimeClassificationDto(
  UUID identifier,
  String name
) { }
