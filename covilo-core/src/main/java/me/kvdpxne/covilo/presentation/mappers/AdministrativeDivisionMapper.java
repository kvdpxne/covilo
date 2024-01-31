package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.AdministrativeDivisionDto;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AdministrativeDivisionMapper
  extends MapStructPresentationMapper<AdministrativeDivision, AdministrativeDivisionDto> {}
