package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    AdministrativeDivisionPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CountryPersistenceMapper
  extends MapStructPersistenceMapper<Country, CountryEntity> {}
