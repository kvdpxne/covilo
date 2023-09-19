package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICountryPersistenceMapper {

  CountryEntity toCountryEntity(final Country source);

  Country toCountry(final CountryEntity source);
}
