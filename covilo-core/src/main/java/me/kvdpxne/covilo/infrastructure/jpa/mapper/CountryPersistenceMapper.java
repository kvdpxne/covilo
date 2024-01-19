package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.infrastructure.jpa.PersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    AdministrativeDivisionPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CountryPersistenceMapper
  extends PersistenceMapper<Country, CountryEntity> {}
