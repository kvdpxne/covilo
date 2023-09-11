package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrimePersistenceMapper {

  CrimeEntity toCrimeEntity(final Crime source);

  Crime toCrime(final CrimeEntity source);
}
