package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserJpaRepository
  extends JpaRepositoryViaIdentifier<UserEntity> {

  Optional<UserEntity> findByEmail(final String email);

  boolean existsByEmail(final String email);
}
