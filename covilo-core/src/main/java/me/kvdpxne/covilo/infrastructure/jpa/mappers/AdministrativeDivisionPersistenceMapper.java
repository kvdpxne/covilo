package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.AdministrativeDivisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AdministrativeDivisionPersistenceMapper
  extends MapStructPersistenceMapper<AdministrativeDivision, AdministrativeDivisionEntity> {}
