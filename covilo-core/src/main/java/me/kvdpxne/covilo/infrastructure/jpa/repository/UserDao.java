package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.RepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao
  extends RepositoryViaIdentifier<UserEntity> {

  Optional<UserEntity> findByEmail(final String email);

  boolean existsByEmail(final String email);
}
