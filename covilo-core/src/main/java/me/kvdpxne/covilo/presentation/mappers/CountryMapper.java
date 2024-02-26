package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Country;
import me.kvdpxne.covilo.presentation.dto.CountryDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    AdministrativeDivisionMapper.class
  }
)
public interface CountryMapper
  extends MapStructPresentationMapper<Country, CountryDto> {}
