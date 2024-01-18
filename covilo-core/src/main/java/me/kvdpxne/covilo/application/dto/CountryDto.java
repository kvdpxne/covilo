package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record CountryDto(
  UUID identifier,
  String name,
  AdministrativeDivisionDto administrativeDivision,
  String continent
) {}
