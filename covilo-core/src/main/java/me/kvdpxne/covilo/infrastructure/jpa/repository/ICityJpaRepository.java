package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityJpaRepository
  extends JpaRepositoryViaIdentifier<CityEntity> {

  Page<CityEntity> findCityEntitiesByProvince_Identifier(final UUID identifier, final Pageable pageable);


}