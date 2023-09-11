package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record City(
  UUID identifier,
  String name,
  String nationalName,
  Province province,
  CapitalType capitalType,
  int population
) {
}
