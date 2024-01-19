package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.PresentationMapper;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CityMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper
  extends PresentationMapper<User, UserDto> {}
