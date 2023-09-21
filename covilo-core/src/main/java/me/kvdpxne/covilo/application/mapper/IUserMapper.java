package me.kvdpxne.covilo.application.mapper;

import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    ICityMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING,
  implementationName = "UserMapperImpl"
)
public interface IUserMapper {

  UserDto toUserDto(final User source);

  User toUser(final UserDto source);
}
