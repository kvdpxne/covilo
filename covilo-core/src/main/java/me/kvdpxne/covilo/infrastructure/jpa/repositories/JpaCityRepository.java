package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCityRepository
  extends JpaIdentifiableRepository<CityEntity> {

  Page<CityEntity> findCityEntitiesByRegion_Identifier(final UUID identifier, final Pageable pageable);


}