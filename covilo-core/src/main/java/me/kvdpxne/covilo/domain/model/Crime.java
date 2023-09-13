package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record Crime(
  UUID identifier,
  String title,
  String description,
  Collection<Category> categories,
  LocalDateTime time,
  User reporter,
  City place,
  boolean confirmed
) {
}
