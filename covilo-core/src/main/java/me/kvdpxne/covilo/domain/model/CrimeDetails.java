package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;

public record CrimeDetails(
  UUID identifier,
  String title,
  String description
) implements Identifiable<UUID> {}
