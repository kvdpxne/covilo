package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.CountryDto;
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
  extends MapStructPresentationMapper<Country, CountryDto> {}
