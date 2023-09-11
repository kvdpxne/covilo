package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.UserPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.UserDao;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserRepositoryAdapter implements UserRepository {

  private final UserDao userDao;
  private final UserPersistenceMapper userPersistenceMapper;

  private User toUserOrNull(final Optional<UserEntity> source) {
    return source.map(this.userPersistenceMapper::toUser).orElse(null);
  }

  @Override
  public User findUserByIdentifierOrNull(final UUID identifier) {
    final var entity = this.userDao.findById(identifier);
    return this.toUserOrNull(entity);
  }

  @Override
  public User findUserByEmailOrNull(final String email) {
    final var entity = this.userDao.findByEmail(email);
    return this.toUserOrNull(entity);
  }

  @Override
  public User insertUser(final User user) {
    var entity = this.userPersistenceMapper.toUserEntity(user);
    entity = this.userDao.save(entity);
    return this.userPersistenceMapper.toUser(entity);
  }

  @Override
  public boolean existsUserByIdentifier(final UUID identifier) {
    return this.userDao.existsById(identifier);
  }

  @Override
  public boolean existsUserByEmail(final String email) {
    return this.userDao.existsByEmail(email);
  }
}
