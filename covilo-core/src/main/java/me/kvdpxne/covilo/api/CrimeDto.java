package me.kvdpxne.covilo.api;

import java.util.Collection;
import java.util.UUID;

public record CrimeDto(
  UUID identifier,
  String description,
  boolean confirmed,
  Collection<CrimeClassificationDto> classifications,
  UserDto reporter,
  CityDto place
) {
}
