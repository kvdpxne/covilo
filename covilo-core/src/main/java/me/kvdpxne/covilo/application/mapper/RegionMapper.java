package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.ProvinceDto;
import me.kvdpxne.covilo.domain.model.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ProvinceMapperImpl"
)
public interface RegionMapper {

  ProvinceDto toRegionDto(
    final Region source
  );
}
