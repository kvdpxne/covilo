package me.kvdpxne.covilo.api;

import me.kvdpxne.covilo.domain.model.CapitalType;

import java.util.UUID;

public record CityDto(
  UUID identifier,
  String name,
  String nationalName,
  CapitalType capital,
  int population
) {
}
