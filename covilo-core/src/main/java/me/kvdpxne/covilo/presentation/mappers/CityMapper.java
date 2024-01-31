package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.CityDto;
import me.kvdpxne.covilo.domain.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    RegionMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CityMapper
  extends MapStructPresentationMapper<City, CityDto> {}
