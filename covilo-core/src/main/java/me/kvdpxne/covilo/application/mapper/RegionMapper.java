package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.PresentationMapper;
import me.kvdpxne.covilo.application.dto.RegionDto;
import me.kvdpxne.covilo.domain.model.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface RegionMapper
  extends PresentationMapper<Region, RegionDto> {}
