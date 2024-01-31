package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.RegionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface RegionPersistenceMapper
  extends MapStructPersistenceMapper<Region, RegionEntity> {}
