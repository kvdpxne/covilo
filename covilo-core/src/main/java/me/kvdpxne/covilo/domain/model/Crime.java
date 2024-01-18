package me.kvdpxne.covilo.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Identity;

@Builder(toBuilder = true)
public record Crime(
  UUID identifier,
  String title,
  String description,
  Collection<Category> categories,
  LocalDateTime time,
  User reporter,
  City place,
  boolean confirmed,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) implements Identity<UUID>, Auditable {}
