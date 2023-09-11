package me.kvdpxne.covilo.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record User(
  UUID identifier,
  String email,
  String password,
  Role role,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  City livingPlace
) {
}
