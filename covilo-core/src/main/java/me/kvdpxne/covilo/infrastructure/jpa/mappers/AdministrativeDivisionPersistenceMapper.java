package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.infrastructure.jpa.entities.AdministrativeDivisionEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper
public interface AdministrativeDivisionPersistenceMapper
  extends MapStructPersistenceMapper<AdministrativeDivision, AdministrativeDivisionEntity> {}
