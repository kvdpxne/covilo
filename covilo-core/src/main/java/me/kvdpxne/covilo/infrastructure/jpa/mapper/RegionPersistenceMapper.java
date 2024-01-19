package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.infrastructure.jpa.PersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entity.RegionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface RegionPersistenceMapper
  extends PersistenceMapper<Region, RegionEntity> {}
