package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    ICategoryPersistenceMapper.class,
    ICityPersistenceMapper.class,
    IUserPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "CrimePersistenceMapperImpl"
)
public interface ICrimePersistenceMapper {

  CrimeEntity toCrimeEntity(final Crime source);

  Crime toCrime(final CrimeEntity source);
}
