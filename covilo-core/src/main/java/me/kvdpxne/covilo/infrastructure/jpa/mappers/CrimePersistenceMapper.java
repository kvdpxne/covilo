package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CrimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CategoryPersistenceMapper.class,
    CityPersistenceMapper.class,
    UserPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CrimePersistenceMapper
  extends MapStructPersistenceMapper<Crime, CrimeEntity> {}
