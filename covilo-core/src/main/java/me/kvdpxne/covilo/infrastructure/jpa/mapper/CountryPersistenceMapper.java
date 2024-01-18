package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    AdministrativeDivisionPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CountryPersistenceMapperImpl"
)
public interface CountryPersistenceMapper {

  CountryEntity toCountryEntity(final Country source);

  Country toCountry(final CountryEntity source);
}
