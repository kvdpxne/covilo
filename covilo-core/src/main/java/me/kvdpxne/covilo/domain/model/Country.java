package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record Country(
  UUID identifier,
  String name,
  AdministrativeDivision administrativeDivision,
  Continent continent
) {}
