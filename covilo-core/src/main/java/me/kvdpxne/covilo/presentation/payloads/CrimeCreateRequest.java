package me.kvdpxne.covilo.presentation.payloads;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public record CrimeCreateRequest(
  LocalDateTime time,
  UUID cityIdentifier,
  UUID classificationIdentifier,
  Collection<UUID> categoryIdentifiers,
  UUID reporterIdentifier,
  boolean confirmed
) {}