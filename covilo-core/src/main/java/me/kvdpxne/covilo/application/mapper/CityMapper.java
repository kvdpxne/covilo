package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.PresentationMapper;
import me.kvdpxne.covilo.application.dto.CityDto;
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
  extends PresentationMapper<City, CityDto> {}
