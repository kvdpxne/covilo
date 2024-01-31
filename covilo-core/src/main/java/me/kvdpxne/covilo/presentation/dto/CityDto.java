package me.kvdpxne.covilo.presentation.dto;

import me.kvdpxne.covilo.domain.model.CapitalType;

import java.util.UUID;

public record CityDto(
  UUID identifier,
  String name,
  String nationalName,
  CapitalType capitalType,
  int population
) {
}
