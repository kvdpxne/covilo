package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.infrastructure.jpa.entities.ClassificationEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ClassificationPersistenceMapper
  extends MapStructPersistenceMapper<Classification, ClassificationEntity> {}
