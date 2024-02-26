package me.kvdpxne.covilo.presentation.dto;

import java.util.UUID;

public record CountryDto(
  UUID identifier,
  String name,
  AdministrativeDivisionDto administrativeDivision,
  String continent
) {}
