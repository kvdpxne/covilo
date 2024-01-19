package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.PresentationMapper;
import me.kvdpxne.covilo.application.dto.CountryDto;
import me.kvdpxne.covilo.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    AdministrativeDivisionMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CountryMapper
  extends PresentationMapper<Country, CountryDto> {}
