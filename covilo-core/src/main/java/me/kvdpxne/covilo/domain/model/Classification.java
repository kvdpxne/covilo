package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Classification(
  UUID identifier,
  String name
) { }
