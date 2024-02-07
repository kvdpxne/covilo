package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import java.util.UUID;

public record CrimeSearchQuery(
  @Parameter()
  UUID placeIdentifier,
  @Parameter()
  UUID classificationIdentifier,
  @Parameter()
  UUID reporterIdentifier,
  @Parameter()
  LocalDate after,
  @Parameter()
  LocalDate before
) {}
