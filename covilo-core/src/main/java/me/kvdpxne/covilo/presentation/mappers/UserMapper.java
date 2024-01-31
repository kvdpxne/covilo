package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.UserDto;
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
  extends MapStructPresentationMapper<User, UserDto> {}
