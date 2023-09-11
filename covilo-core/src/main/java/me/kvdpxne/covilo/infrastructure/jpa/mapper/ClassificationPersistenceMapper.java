package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ClassificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassificationPersistenceMapper {

  ClassificationEntity toClassificationEntity(final Classification source);

  Classification toClassification(final ClassificationEntity source);
}
