package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    IProvincePersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CityPersistenceMapperImpl"
)
public interface ICityPersistenceMapper {

  CityEntity toCityEntity(final City source);

  City toCity(final CityEntity source);
}
