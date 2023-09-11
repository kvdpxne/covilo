package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityPersistenceMapper {

  CityEntity toCityEntity(final City source);

  City toCity(final CityEntity source);
}
