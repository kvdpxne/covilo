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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling user-related endpoints.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = Endpoints.USER)
@RestController
public final class UserController {

  /**
   * Service responsible for user-related operations.
   */
  private final UserServicePort userService;

  /**
   * Mapper for converting between user entities and DTOs.
   */
  private final UserMapper userMapper;

  /**
   * Retrieves information about a user identified by the given identifier.
   *
   * @param identifier The unique identifier of the user to retrieve.
   * @return The data transfer object (DTO) representing the user.
   */
  @GetMapping(
    path = "{identifier}"
  )
  public UserDto getUser(
    @PathVariable
    final UUID identifier
  ) {
    // Retrieves the user information by identifier from the userService
    // and maps it to a DTO using the userMapper.
    return this.userMapper.toDto(
      this.userService.getUserByIdentifier(identifier)
    );
  }

  /**
   * Updates the email of a user identified by the given identifier.
   *
   * @param identifier The unique identifier of the user whose email is to be
   *                   updated.
   * @param request    The request object containing the new email.
   */
  @PutMapping(
    path = "{identifier}/email"
  )
  public void updateUserEmail(
    @PathVariable
    final UUID identifier,
    @RequestBody
    final UpdateUserEmailRequest request
  ) {
    // /Updates the email of the user identified by the given identifier
    // using the new email from the request.
    this.userService.updateUserEmail(
      this.userService.getUserByIdentifier(identifier),
      request.newEmail()
    );
  }

  /**
   * Updates the password of a user identified by the given identifier.
   *
   * @param identifier The unique identifier of the user whose password is to
   *                   be updated.
   * @param request    The request object containing the new password.
   */
  @PutMapping(
    path = "{identifier}/password"
  )
  public void updateUserPassword(
    @PathVariable
    final UUID identifier,
    @RequestBody
    final UpdateUserPasswordRequest request
  ) {
    // Updates the password of the user identified by the given identifier
    // using the new password from the request.
    this.userService.updateUserPassword(
      this.userService.getUserByIdentifier(identifier),
      request.newPassword()
    );
  }

  /**
   * Creates a new user based on the information provided in the request.
   *
   * @param request The request object containing information about the new
   *                user.
   */
  @PostMapping
  public void createUser(
    @RequestBody
    final CreateNewUserRequest request
  ) {
    // Delegates user creation to the userService using the userMapper to
    // convert the request to user entity.
    this.userService.createUser(
      this.userMapper.fromRequest(request)
    );
  }

  /**
   * Deletes a user identified by the given identifier.
   *
   * @param identifier The unique identifier of the user to be deleted.
   * @throws UserNotFoundException If the user with the specified identifier is
   *                               not found.
   */
  @DeleteMapping(
    path = "{identifier}"
  )
  public void deleteUser(
    @PathVariable
    final UUID identifier
  ) throws UserNotFoundException {
    // Deletes the user identified by the given identifier using the
    // userService.
    this.userService.deleteUserByIdentifier(identifier);
  }
}
