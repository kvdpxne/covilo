package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRegionRepository
  extends JpaIdentifiableRepository<RegionEntity> {

  Page<RegionEntity> findProvinceEntitiesByCountry_Identifier(UUID identifier, Pageable pageable);


}