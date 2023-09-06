package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Crime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrimeMapper {

  Crime toDomain(final CrimeDto source);

  Crime toDomain(final ReportCrimeRequest request);

  CrimeDto toDto(final Crime source);

  CategoryDto toDto(final Category source);
}
