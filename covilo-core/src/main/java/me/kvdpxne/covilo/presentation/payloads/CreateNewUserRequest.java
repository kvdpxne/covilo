package me.kvdpxne.covilo.presentation.payloads;

import java.time.LocalDate;
import me.kvdpxne.covilo.domain.model.Gender;

public record CreateNewUserRequest(
  String email,
  String password,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate
) {}
