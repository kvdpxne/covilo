package me.kvdpxne.covilo.domain.persistence.specifications;

import java.time.LocalDate;
import java.util.UUID;

public record CrimeSearchCriteria(
  UUID placeIdentifier,
  UUID classificationIdentifier,
  UUID reporterIdentifier,
  LocalDate after,
  LocalDate before
) {}
