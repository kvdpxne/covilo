package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaUserRepository
  extends JpaRepositoryViaIdentifier<UserEntity> {

  Optional<UserEntity> findByEmail(final String email);

  @Transactional
  @Modifying
  @Query("update UserEntity u set u.email = ?1, u.lastModifiedDate = ?2 where u.identifier = ?3")
  int updateEmailByIdentifier(
    final UUID identifier,
    final String email,
    final LocalDateTime lastModifiedDate
  );

  @Transactional
  @Modifying
  @Query("update UserEntity u set u.password = ?1, u.lastModifiedDate = ?2 where u.identifier = ?3")
  int updatePasswordByIdentifier(
    final UUID identifier,
    final String password,
    final LocalDateTime lastModifiedDate
  );

  boolean existsByEmail(final String email);
}
