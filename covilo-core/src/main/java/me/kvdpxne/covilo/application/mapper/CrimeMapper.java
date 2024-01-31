package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.domain.model.Crime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CategoryMapper.class,
    CityMapper.class,
    UserMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CrimeMapperImpl"
)
public interface CrimeMapper
  extends MapStructPresentationMapper<Crime, CrimeDto> {

  Crime toDomain(
    final ReportCrimeRequest request
  );
}
