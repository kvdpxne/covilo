package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Token;

public interface TokenRepository {

  Collection<Token> findValidTokensByUserIdentifier(final UUID identifier);

  Token findTokenByIdentifierOrNull(final UUID identifier);
  Token findTokenByTokenOrNull(final String token);

  Token insertToken(final Token token);
  void insertTokens(final Collection<Token> tokens);
}
