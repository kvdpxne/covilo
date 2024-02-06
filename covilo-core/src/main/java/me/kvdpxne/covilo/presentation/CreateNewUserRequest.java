package me.kvdpxne.covilo.presentation;

import java.time.LocalDate;

public record CreateNewUserRequest(
  String email,
  String password,
  String firstName,
  String lastName,
  LocalDate birthDate
) {}
