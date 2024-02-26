package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CountryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCountryRepository
  extends JpaIdentifiableRepository<CountryEntity> {

  Optional<CountryEntity> findByNameAllIgnoreCase(String name);


}