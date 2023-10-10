package me.kvdpxne.covilo.presentation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.domain.service.UserLifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "api/0.1.0/user")
@RestController
public final class UserController {

  private final UserLifecycleService userLifecycleService;
  private final IUserMapper userMapper;

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping(path = "{identifier}")
  public @ResponseBody UserDto getUser(
    @PathVariable final UUID identifier
  ) throws UserNotFoundException {

    return this.userMapper.toUserDto(
      this.userLifecycleService.getUserByIdentifier(identifier)
    );
  }

  @ResponseStatus(code = HttpStatus.OK)
  @DeleteMapping(path = "{identifier}")
  public void deleteUser(
    @PathVariable final UUID identifier
  ) throws UserNotFoundException {
    // If the method does not find a user with the given identifier, it throws
    // a UserNotFoundException exception.
    this.userLifecycleService.deleteUserByIdentifier(identifier);
  }
}
