package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.domain.model.Crime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    ICategoryMapper.class,
    CityMapper.class,
    IUserMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CrimeMapperImpl"
)
public interface ICrimeMapper {

  CrimeDto toCrimeDto(final Crime source);

  Crime toCrime(final CrimeDto source);

  Crime toCrime(final ReportCrimeRequest request);
}
