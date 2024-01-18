package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record AdministrativeDivisionDto(
  String name,
  UUID identifier
) {}
