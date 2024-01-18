package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Region;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CountryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ProvincePersistenceMapperImpl"
)
public interface ProvincePersistenceMapper {

  ProvinceEntity toProvinceEntity(final Region source);

  Region toProvince(final ProvinceEntity source);
}
