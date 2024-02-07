package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;
import me.kvdpxne.covilo.presentation.CrimeSearchQuery;
import me.kvdpxne.covilo.presentation.dto.CrimeDto;
import me.kvdpxne.covilo.presentation.payloads.ReportCrimeRequest;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    CategoryMapper.class,
    CityMapper.class,
    ClassificationMapper.class,
    UserMapper.class
  }
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
