package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCrimeRepository
  extends JpaRepositoryViaIdentifier<CrimeEntity> {

  Page<CrimeEntity> findCrimeEntityByPlace_Identifier(final UUID identifier, final Pageable pageable);

  Page<CrimeEntity> findByPlace_Identifier(UUID identifier, Pageable pageable);
}