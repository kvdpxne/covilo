package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.infrastructure.jpa.entities.RegionEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    CountryPersistenceMapper.class
  }
)
public interface RegionPersistenceMapper
  extends MapStructPersistenceMapper<Region, RegionEntity> {}
