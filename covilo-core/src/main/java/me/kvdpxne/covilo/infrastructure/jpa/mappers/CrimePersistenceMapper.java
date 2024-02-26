package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CrimeEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    CategoryPersistenceMapper.class,
    CityPersistenceMapper.class,
    ClassificationPersistenceMapper.class,
    UserPersistenceMapper.class
  }
)
public interface CrimePersistenceMapper
  extends MapStructPersistenceMapper<Crime, CrimeEntity> {}
