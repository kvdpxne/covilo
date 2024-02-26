package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CityEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    RegionPersistenceMapper.class
  }
)
public interface CityPersistenceMapper
  extends MapStructPersistenceMapper<City, CityEntity> {}
