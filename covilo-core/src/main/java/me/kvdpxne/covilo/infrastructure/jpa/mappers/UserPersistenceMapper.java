package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CityPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserPersistenceMapper
  extends MapStructPersistenceMapper<User, UserEntity> {}
