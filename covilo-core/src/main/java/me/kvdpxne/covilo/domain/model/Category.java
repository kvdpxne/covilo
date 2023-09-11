package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Category(
  UUID identifier,
  String name,
  Classification classification
) {
}