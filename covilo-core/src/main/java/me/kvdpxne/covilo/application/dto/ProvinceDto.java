package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record ProvinceDto(
  UUID identifier,
  String name,
  String nationalName
) {
}
