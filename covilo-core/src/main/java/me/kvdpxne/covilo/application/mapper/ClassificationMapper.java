package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.application.dto.ClassificationDto;
import me.kvdpxne.covilo.domain.model.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ClassificationMapper
  extends MapStructPresentationMapper<Classification, ClassificationDto> {}
