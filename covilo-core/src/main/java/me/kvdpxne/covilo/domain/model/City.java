package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record City(
  UUID identifier,
  String name,
  String nationalName,
  Region province,
  CapitalType capitalType,
  int population
) {}
