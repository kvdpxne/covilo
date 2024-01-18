package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.TokenEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.TokenPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.UserPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaTokenRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class TokenDao implements TokenRepository {

  private final JpaTokenRepository repository;
  private final TokenPersistenceMapper mapper;

  private final UserPersistenceMapper userMapper;

  /**
   *
   */
  private Token toTokenOrNull(final Optional<TokenEntity> source) {
    return source.map(this.mapper::toToken).orElse(null);
  }

  @Override
  public Collection<Token> findValidTokensByUserIdentifier(final UUID identifier) {
    return this.repository.findAllValidTokenByUser(identifier)
      .stream()
      .map(this.mapper::toToken)
      .toList();
  }

  @Override
  public Optional<Token> findTokenByUserIdentifier(final UUID identifier) {
    return this.repository.findTokenByUser_Identifier(identifier)
      .map(this.mapper::toToken);
  }

  @Override
  public User findUserByCompactTokenOrNull(final String compactToken) {
    return this.repository.findTokenByCompactToken(compactToken)
      .map(token -> this.userMapper.toUser(token.getUser()))
      .orElse(null);
  }

  @Override
  public Token findTokenByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return this.toTokenOrNull(entity);
  }

  @Override
  public Optional<Token> findTokenByCompactToken(final String token) {
    return this.repository.findTokenByCompactToken(token)
      .map(this.mapper::toToken);
  }

  @Override
  public Token insertToken(final Token token) {
    var entity = this.mapper.toTokenEntity(token);
    entity = this.repository.save(entity);
    return this.mapper.toToken(entity);
  }

  @Override
  public void deleteToken(final Token token) {
    this.repository.deleteById(token.identifier());
  }

  @Override
  public void deleteAllTokensByUser(final User user) {
    final var tokens = this.repository.findByUser_Identifier(user.identifier());
    this.repository.deleteAll(tokens);
  }
}
