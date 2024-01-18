package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.infrastructure.jpa.entity.AdministrativeDivisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AdministrativeDivisionPersistenceMapper {

  /**
   *
   */
  AdministrativeDivisionEntity toAdministrativeDivisionEntity(
    final AdministrativeDivision source
  );

  /**
   *
   */
  AdministrativeDivision toAdministrativeDivision(
    final AdministrativeDivisionEntity source
  );
}
