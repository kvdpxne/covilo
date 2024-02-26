package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.User;

public interface TokenRepository {

  Collection<Token> findValidTokensByUserIdentifier(final UUID identifier);

  Optional<Token> findTokenByUserIdentifier(final UUID identifier);
  Token findTokenByIdentifierOrNull(final UUID identifier);
  Optional<Token> findTokenByCompactToken(final String token);

  Token insertToken(final Token token);

  void deleteToken(final Token token);

  void deleteAllTokensByUser(final User user);


  User findUserByCompactTokenOrNull(final String compactToken);
}
