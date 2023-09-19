package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryJpaRepository
  extends JpaRepositoryViaIdentifier<CountryEntity> {

  Optional<CountryEntity> findByNameAllIgnoreCase(String name);


}