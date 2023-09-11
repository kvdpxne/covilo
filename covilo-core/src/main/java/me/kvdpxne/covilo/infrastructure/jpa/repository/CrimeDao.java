package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.RepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrimeDao
  extends RepositoryViaIdentifier<CrimeEntity> {

  Page<CrimeEntity> findByPlace_Identifier(UUID identifier, Pageable pageable);
}