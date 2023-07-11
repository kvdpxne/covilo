package me.kvdpxne.covilo.api;

import me.kvdpxne.covilo.domain.model.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
  UUID identifier,
  String recognizableName,
  String email,
  Gender gender,
  LocalDate birthDate
) {
}