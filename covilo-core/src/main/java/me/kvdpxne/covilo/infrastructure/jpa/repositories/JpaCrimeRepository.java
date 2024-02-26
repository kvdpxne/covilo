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

  Page<CrimeEntity> findByPlace_Identifier(UUID identifier, Pageable pageable);

  Page<CrimeEntity> findByClassification_Identifier(UUID identifier, Pageable pageable);

  Page<CrimeEntity> findByReporter_Identifier(UUID identifier, Pageable pageable);


}