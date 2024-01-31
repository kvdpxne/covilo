package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.application.dto.RegionDto;
import me.kvdpxne.covilo.domain.model.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    AdministrativeDivisionMapper.class,
    CountryMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface RegionMapper
  extends MapStructPresentationMapper<Region, RegionDto> {}
