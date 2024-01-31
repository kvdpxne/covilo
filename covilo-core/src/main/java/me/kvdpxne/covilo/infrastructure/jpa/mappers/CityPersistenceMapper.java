package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    RegionPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CityPersistenceMapper
  extends MapStructPersistenceMapper<City, CityEntity> {}
