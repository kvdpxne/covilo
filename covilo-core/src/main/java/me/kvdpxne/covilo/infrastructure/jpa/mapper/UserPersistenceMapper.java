package me.kvdpxne.covilo.infrastructure.jpa.mapper;

import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.infrastructure.jpa.PersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CityPersistenceMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserPersistenceMapper
  extends PersistenceMapper<User, UserEntity> {}
