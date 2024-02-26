package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.presentation.dto.CityDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper
  extends MapStructPresentationMapper<City, CityDto> {}
