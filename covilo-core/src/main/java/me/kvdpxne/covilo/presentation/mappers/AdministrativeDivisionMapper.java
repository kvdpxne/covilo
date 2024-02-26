package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.presentation.dto.AdministrativeDivisionDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper
public interface AdministrativeDivisionMapper
  extends MapStructPresentationMapper<AdministrativeDivision, AdministrativeDivisionDto> {}
