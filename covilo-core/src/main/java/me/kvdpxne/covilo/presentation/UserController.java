package me.kvdpxne.covilo.presentation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.presentation.dto.UserDto;
import me.kvdpxne.covilo.presentation.mappers.UserMapper;
import me.kvdpxne.covilo.presentation.payloads.UpdateUserEmailRequest;
import me.kvdpxne.covilo.presentation.payloads.UpdateUserPasswordRequest;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.port.out.UserServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = Endpoints.USER)
@RestController
public final class UserController {

  private final UserServicePort userServicePort;
  private final UserMapper userMapper;

  @GetMapping(
    path = "{identifier}"
  )
  public UserDto getUser(
    @PathVariable
    final UUID identifier
  ) {
    return this.userMapper.toDto(
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
