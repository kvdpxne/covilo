package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.infrastructure.jpa.entities.UserEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mappers.UserPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repositories.JpaUserRepository;
import me.kvdpxne.covilo.shared.SpringPageRequestMapper;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public final class UserDao
  implements UserRepository {

  private final JpaUserRepository userRepository;
  private final UserPersistenceMapper userPersistenceMapper;

  private final SpringPageRequestMapper pageRequestMapper;

  private User toUserOrNull(final Optional<UserEntity> source) {
    return source.map(this.userPersistenceMapper::toDomain).orElse(null);
  }

  @Override
  public Iterable<User> findUsers(final PageRange range) {
    return this.userRepository.findAll(
      this.pageRequestMapper.toPageRequest(range)
    ).map(this.userPersistenceMapper::toDomain);
  }

  @Override
  public User findUserByIdentifierOrNull(final UUID identifier) {
    final var entity = this.userRepository.findById(identifier);
    return this.toUserOrNull(entity);
  }

  @Override
  public User findUserByEmailOrNull(final String email) {
    final var entity = this.userRepository.findByEmail(email);
    return this.toUserOrNull(entity);
  }

  @Override
  public User insert(final User user) {
    var entity = this.userPersistenceMapper.toDao(user);
    entity = this.userRepository.save(entity);
    return this.userPersistenceMapper.toDomain(entity);
  }

  @Override
  public boolean updateLastModifiedDateByIdentifier(
    final UUID identifier
  ) {
    return 0 != this.userRepository.updateLastModifiedDateByIdentifier(
      LocalDateTime.now(),
      identifier
    );
  }

  @Override
  public boolean updateEmailByIdentifier(
    final UUID identifier,
    final String email
  ) {
    // The number of rows changed.
    int rows = 0;

    try {
      rows = this.userRepository.updateEmailByIdentifier(
        identifier,
        email,
        LocalDateTime.now()
      );
    } catch (final Throwable reason) {
      logger.atError()
        .setMessage("The request could not be handled for an unhandled reason.")
        .setCause(reason.getCause())
        .log();
    }

    return 0 != rows;
  }

  @Override
  public boolean updatePasswordByIdentifier(
    final UUID identifier,
    final String password
  ) {
    // The number of rows changed.
    int rows = 0;

    try {
      rows = this.userRepository.updatePasswordByIdentifier(
        identifier,
        password,
        LocalDateTime.now()
      );
    } catch (final Throwable reason) {
      logger.atError()
        .setMessage("For an unhandled reason, the email address could not be" +
          "updated for the user with the identifier: {}")
        .setCause(reason.getCause())
        .addArgument(identifier)
        .log();
    }

    return 0 != rows;
  }

  @Override
  public void deleteUserByIdentifier(final UUID identifier) {
    this.userRepository.deleteById(identifier);
  }

  @Override
  public boolean existsUserByIdentifier(final UUID identifier) {
    return this.userRepository.existsById(identifier);
  }

  @Override
  public boolean existsUserByEmail(final String email) {
    return this.userRepository.existsByEmail(email);
  }
}
