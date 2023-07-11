package me.kvdpxne.covilo.api;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Crime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrimeMapper {

  CategoryDto toResponse(final Category source);

  CrimeDto toResponse(final Crime source);
}
