package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record Province(
  UUID identifier,
  String name,
  String nationalName,
  Country country
) {
}