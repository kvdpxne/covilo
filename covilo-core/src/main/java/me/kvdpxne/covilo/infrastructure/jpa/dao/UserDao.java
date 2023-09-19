package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.IUserRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.IUserPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.IUserJpaRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class UserDao
  implements IUserRepository {

  private final IUserJpaRepository repository;
  private final IUserPersistenceMapper mapper;

  private User toUserOrNull(final Optional<UserEntity> source) {
    return source.map(this.mapper::toUser).orElse(null);
  }

  @Override
  public User findUserByIdentifierOrNull(final UUID identifier) {
    final var entity = this.repository.findById(identifier);
    return this.toUserOrNull(entity);
  }

  @Override
  public User findUserByEmailOrNull(final String email) {
    final var entity = this.repository.findByEmail(email);
    return this.toUserOrNull(entity);
  }

  @Override
  public User insertUser(final User user) {
    var entity = this.mapper.toUserEntity(user);
    entity = this.repository.save(entity);
    return this.mapper.toUser(entity);
  }

  @Override
  public boolean existsUserByIdentifier(final UUID identifier) {
    return this.repository.existsById(identifier);
  }

  @Override
  public boolean existsUserByEmail(final String email) {
    return this.repository.existsByEmail(email);
  }
}
