package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;
import me.kvdpxne.covilo.presentation.CrimeSearchQuery;
import me.kvdpxne.covilo.presentation.dto.CrimeDto;
import me.kvdpxne.covilo.presentation.payloads.ReportCrimeRequest;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
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

  /**
   *
   */
  CrimeSearchCriteria toCrimeSearchCriteria(
    final CrimeSearchQuery query
  );

  Crime fromCreateRequest(
    final ReportCrimeRequest request
  );
}
