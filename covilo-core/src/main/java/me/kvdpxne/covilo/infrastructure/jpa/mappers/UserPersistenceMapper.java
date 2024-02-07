package me.kvdpxne.covilo.infrastructure.jpa.mappers;

import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.infrastructure.jpa.entities.UserEntity;
import me.kvdpxne.covilo.shared.MapStructPersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(
  uses = {
    CityPersistenceMapper.class
  }
)
public interface UserPersistenceMapper
  extends MapStructPersistenceMapper<User, UserEntity> {}
