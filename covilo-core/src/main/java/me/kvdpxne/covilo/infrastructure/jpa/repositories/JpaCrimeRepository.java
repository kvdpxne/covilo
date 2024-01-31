package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CrimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCrimeRepository
  extends JpaIdentifiableRepository<CrimeEntity> {

  Page<CrimeEntity> findCrimeEntityByPlace_Identifier(final UUID identifier, final Pageable pageable);

  Page<CrimeEntity> findByPlace_Identifier(UUID identifier, Pageable pageable);
}