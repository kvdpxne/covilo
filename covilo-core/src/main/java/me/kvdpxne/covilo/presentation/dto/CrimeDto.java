package me.kvdpxne.covilo.presentation.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public record CrimeDto(
  UUID identifier,
  String title,
  String description,
  Collection<CategoryDto> categories,
  LocalDateTime time,
  UserDto reporter,
  CityDto place,
  boolean confirmed
) {
}
