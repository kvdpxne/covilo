package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.presentation.dto.ClassificationDto;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ClassificationMapper
  extends MapStructPresentationMapper<Classification, ClassificationDto> {}
