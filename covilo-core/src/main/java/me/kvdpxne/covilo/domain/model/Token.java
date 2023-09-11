package me.kvdpxne.covilo.domain.model;

import java.util.UUID;

public record Token(
  UUID identifier,
  String token,
  TokenType tokenType,
  boolean revoked,
  boolean expired
) {
}
