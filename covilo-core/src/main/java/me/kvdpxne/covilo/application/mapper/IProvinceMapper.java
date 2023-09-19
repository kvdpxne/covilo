package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.ProvinceDto;
import me.kvdpxne.covilo.domain.model.Province;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ProvinceMapperImpl"
)
public interface IProvinceMapper {

  ProvinceDto toProvinceDto(final Province source);

  Province toProvince(final ProvinceDto source);
}
