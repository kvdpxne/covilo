package me.kvdpxne.covilo.application;

import java.util.UUID;
import me.kvdpxne.covilo.application.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;

public interface IUserLifecycleService {

  User getUserByIdentifier(final UUID identifier) throws UserNotFoundException;

  User getUserByEmail(final String email) throws UserNotFoundException;

  /**
   * Creates a new user from the given user data if the unique identifier
   * given in the data does not exist in the database.
   *
   * @throws UserAlreadyExistsException If the unique user identifier already
   *                                    exists in the database.
   */
  User createUser(final User user) throws UserAlreadyExistsException;

  void updateUserEmail(final User user, final String newEmail) throws UserNotFoundException;

  void updateUserPassword(final User user, final String newPassword) throws UserNotFoundException;

  void updateUser(final User user) throws UserNotFoundException;

  void deleteUserByIdentifier(final UUID identifier) throws UserNotFoundException;

  void deleteUser(final User user) throws UserNotFoundException;

  /**
   * @throws NullPointerException If the given email address is null.
   * @throws IllegalArgumentException
   */
  boolean checkUserExistsByEmail(final String email);
}
