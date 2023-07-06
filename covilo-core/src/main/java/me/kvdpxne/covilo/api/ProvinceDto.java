package me.kvdpxne.covilo.api;

import java.util.UUID;

public record ProvinceDto(
  UUID identifier,
  String name,
  String nationalName
) {
}
