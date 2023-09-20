package me.kvdpxne.covilo.application.dto;

import java.time.LocalDateTime;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.model.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
  UUID identifier,
  String email,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  CityDto livingPlace,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) {
}
