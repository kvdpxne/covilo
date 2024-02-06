package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.presentation.CreateNewUserRequest;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import me.kvdpxne.covilo.presentation.dto.UserDto;
import me.kvdpxne.covilo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
  uses = {
    CityMapper.class
  },
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper
  extends MapStructPresentationMapper<User, UserDto> {

  @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "identifier", expression = "java(java.util.UUID.randomUUID())")
  User fromRequest(final CreateNewUserRequest request);
}
