package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Token;

public interface TokenRepository {

  Token findTokenByIdentifierOrNull(final UUID identifier);
  Token findTokenByTokenOrNull(final String token);

  Token insertToken(final Token token);
}
