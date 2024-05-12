package me.kvdpxne.covilo.presentation.mappers;

import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.presentation.dto.UserDto;
import me.kvdpxne.covilo.presentation.payloads.CreateNewUserRequest;
import me.kvdpxne.covilo.presentation.payloads.SignupRequest;
import me.kvdpxne.covilo.shared.MapStructPresentationMapper;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between User entities and UserDto DTOs.
 */
@Mapper(
  uses = {
  }
)
public interface UserMapper
  extends MapStructPresentationMapper<User, UserDto> {

  /**
   * Converts a CreateNewUserRequest to a User entity.
   *
   * @param request The CreateNewUserRequest to convert.
   * @return The corresponding User entity.
   */
  User fromRequest(final CreateNewUserRequest request);

  /**
   * Converts a SignupRequest to a User entity.
   *
   * @param request The SignupRequest to convert.
   * @return The corresponding User entity.
   */
  User fromRequest(final SignupRequest request);
}
