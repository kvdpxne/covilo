package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.infrastructure.jpa.PersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
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
  extends PersistenceMapper<Crime, CrimeEntity> {}
