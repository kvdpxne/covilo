package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.PresentationMapper;
import me.kvdpxne.covilo.application.dto.AdministrativeDivisionDto;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AdministrativeDivisionMapper
  extends PresentationMapper<AdministrativeDivision, AdministrativeDivisionDto> {}
