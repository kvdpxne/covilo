package me.kvdpxne.covilo.presentation.dto;

import java.util.UUID;

public record RegionDto(
  UUID identifier,
  String name,
  String nationalName
) {
}
