package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.ClassificationDto;
import me.kvdpxne.covilo.domain.model.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "ClassificationMapperImpl"
)
public interface IClassificationMapper {

  ClassificationDto toClassificationDto(final Classification source);

  Classification toClassification(final ClassificationDto source);
}
