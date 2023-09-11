package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.payload.ReportCrimeRequest;
import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.application.dto.CrimeDto;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CategoryEntity;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrimeMapper {

  CrimeEntity toDomain(final CrimeDto source);

  CrimeEntity toDomain(final ReportCrimeRequest request);

  CrimeDto toDto(final CrimeEntity source);

  CategoryDto toDto(final CategoryEntity source);
}
