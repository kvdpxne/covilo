package me.kvdpxne.covilo.presentation.payloads;

import java.time.LocalDate;
import me.kvdpxne.covilo.domain.model.Gender;

public record SignupRequest(
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  String email,
  String password,
  String confirmPassword,
  boolean privacyPolicy
) {}