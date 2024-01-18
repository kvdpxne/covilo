package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.infrastructure.jpa.entity.RegionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ProvincePersistenceMapperImpl"
)
public interface RegionPersistenceMapper {

  RegionEntity toProvinceEntity(final Region source);

  Region toProvince(final RegionEntity source);
}
