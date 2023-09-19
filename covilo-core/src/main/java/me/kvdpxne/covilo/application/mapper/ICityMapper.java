package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.CityDto;
import me.kvdpxne.covilo.domain.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CityMapperImpl"
)
public interface ICityMapper {

  CityDto toCityDto(final City source);

  City toCity(final CityDto source);
}
