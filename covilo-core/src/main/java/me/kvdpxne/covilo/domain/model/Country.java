package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Country(
  UUID identifier,
  String name,
  AdministrativeDivisionType administrativeDivisionType,
  Continent continent
) {
}
