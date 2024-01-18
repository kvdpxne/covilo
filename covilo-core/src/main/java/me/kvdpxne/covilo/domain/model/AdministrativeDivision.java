package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record AdministrativeDivision(
  UUID identifier,
  String name
) {}
