package me.kvdpxne.covilo.presentation.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public record CrimeDto(
  String identifier,
  String title,
  String description,
  Collection<CategoryDto> categories,
  LocalDateTime time,
  UserDto reporter,
  boolean confirmed
) {
}
