package me.kvdpxne.covilo.presentation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.application.payload.UpdateUserEmailRequest;
import me.kvdpxne.covilo.application.payload.UpdateUserPasswordRequest;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "api/0.1.0/user")
@RestController
public final class UserController {

  private final UserServicePort userServicePort;
  private final IUserMapper userMapper;

  @GetMapping(
    path = "{identifier}"
  )
  public UserDto getUser(
    @PathVariable
    final UUID identifier
  ) {
    return this.userMapper.toUserDto(
      this.userServicePort.getUserByIdentifier(identifier)
    );
  }
  @PutMapping(
    path = "{identifier}/email"
  )
  public void updateUserEmail(
    @PathVariable
    final UUID identifier,
    @RequestBody
    final UpdateUserEmailRequest request
  ) {
    this.userServicePort.updateUserEmail(
      this.userServicePort.getUserByIdentifier(identifier),
      request.newEmail()
    );
  }

  @PutMapping(
    path = "{identifier}/password"
  )
  public void updateUserPassword(
    @PathVariable
    final UUID identifier,
    @RequestBody
    final UpdateUserPasswordRequest request
  ) {
    this.userServicePort.updateUserPassword(
      this.userServicePort.getUserByIdentifier(identifier),
      request.newPassword()
    );
  }

  @ResponseStatus(code = HttpStatus.OK)
  @DeleteMapping(path = "{identifier}")
  public void deleteUser(
    @PathVariable final UUID identifier
  ) throws UserNotFoundException {
    // If the method does not find a user with the given identifier, it throws
    // a UserNotFoundException exception.
    this.userServicePort.deleteUserByIdentifier(identifier);
  }
}
