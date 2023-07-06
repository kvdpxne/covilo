package me.kvdpxne.covilo.api;

import java.util.UUID;

public record CountryDto(
  UUID identifier,
  String name,
  String continent
) { }
