package me.kvdpxne.covilo.infrastructure.jpa.repository;

import java.util.UUID;
import me.kvdpxne.covilo.infrastructure.jpa.JpaRepositoryViaIdentifier;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRegionRepository
  extends JpaRepositoryViaIdentifier<ProvinceEntity> {

  Page<ProvinceEntity> findProvinceEntitiesByCountry_Identifier(UUID identifier, Pageable pageable);


}