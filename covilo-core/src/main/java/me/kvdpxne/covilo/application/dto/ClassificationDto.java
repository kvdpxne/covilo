package me.kvdpxne.covilo.application.dto;

import java.util.UUID;

public record ClassificationDto(
  UUID identifier,
  String name
) {
}
