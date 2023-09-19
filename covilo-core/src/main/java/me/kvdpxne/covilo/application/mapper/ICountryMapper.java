package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.CountryDto;
import me.kvdpxne.covilo.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CountryMapperImpl"
)
public interface ICountryMapper {

  CountryDto toCountryDto(final Country source);

  Country toCountry(final CountryDto source);
}
