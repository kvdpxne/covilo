package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CountryEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    AdministrativeDivisionPersistenceMapper.class
  }
)
public interface CountryPersistenceMapper
  extends MapStructPersistenceMapper<Country, CountryEntity> {}
