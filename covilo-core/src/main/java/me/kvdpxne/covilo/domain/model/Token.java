package me.kvdpxne.covilo.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record Token(
  UUID identifier,
  String token,
  TokenType tokenType,
  User user,
  boolean revoked,
  boolean expired
) {


}
