package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Province(
  UUID identifier,
  String name,
  String nationalName,
  Country country
) {
}