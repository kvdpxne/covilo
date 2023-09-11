package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.RepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;

public interface CountryDao
  extends RepositoryViaIdentifier<CountryEntity> {

  Optional<CountryEntity> findByNameAllIgnoreCase(String name);


}