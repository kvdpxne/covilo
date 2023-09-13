package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.TokenPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.TokenDao;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class TokenRepositoryAdapter implements TokenRepository {

  private final TokenDao tokenDao;
  private final TokenPersistenceMapper tokenPersistenceMapper;

  /**
   *
   */
  private Token toTokenOrNull(final Optional<TokenEntity> source) {
    return source.map(this.tokenPersistenceMapper::toToken).orElse(null);
  }

  @Override
  public Collection<Token> findValidTokensByUserIdentifier(final UUID identifier) {
    return this.tokenDao.findAllValidTokenByUser(identifier)
      .stream()
      .map(this.tokenPersistenceMapper::toToken)
      .toList();
  }

  @Override
  public Token findTokenByIdentifierOrNull(final UUID identifier) {
    final var entity = this.tokenDao.findById(identifier);
    return this.toTokenOrNull(entity);
  }

  @Override
  public Token findTokenByTokenOrNull(final String token) {
    final var entity = this.tokenDao.findByToken(token);
    return this.toTokenOrNull(entity);
  }

  @Override
  public Token insertToken(final Token token) {
    var entity = this.tokenPersistenceMapper.toTokenEntity(token);
    entity = this.tokenDao.save(entity);
    return this.tokenPersistenceMapper.toToken(entity);
  }

  @Override
  public void insertTokens(final Collection<Token> tokens) {
    tokens.forEach(this::insertToken);
  }
}
