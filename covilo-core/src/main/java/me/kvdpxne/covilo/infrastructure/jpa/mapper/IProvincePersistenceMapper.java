package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Province;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    ICountryPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ProvincePersistenceMapperImpl"
)
public interface IProvincePersistenceMapper {

  ProvinceEntity toProvinceEntity(final Province source);

  Province toProvince(final ProvinceEntity source);
}
