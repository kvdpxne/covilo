package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record RegionDto(
  UUID identifier,
  String name,
  String nationalName
) {
}
