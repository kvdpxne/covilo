package me.kvdpxne.covilo.domain.port.out;

import java.util.UUID;
import me.kvdpxne.covilo.common.exceptions.UserInvalidEmailAddressException;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;

public interface UserServicePort {

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

  /**
   *
   */
  void updateLastModifiedDate(
    final User user
  );

  /**
   * Updates the email address for the specified user.
   *
   * @throws NullPointerException             If the given user or new email address
   *                                          is null.
   * @throws IllegalArgumentException         If the new email address given is an
   *                                          empty string or consists only of white
   *                                          characters.
   * @throws UserInvalidEmailAddressException If the new email address provided is
   *                                          not a valid email address.
   * @throws UserNotFoundException            If the specified user does not exist.
   */
  void updateUserEmail(
    final User user,
    final String newEmail
  );

  /**
   * Updates the password of the specified user.
   *
   * @throws NullPointerException         If the specified user or new password is
   *                                      null.
   * @throws IllegalArgumentException     If the new password provided is empty or
   *                                      consists only of whitespace.
   * @throws UserInvalidPasswordException If the password provided is incorrect or
   *                                      does not meet basic password standards.
   * @throws UserNotFoundException        If the specified user does not exist.
   */
  void updateUserPassword(
    final User user,
    final String newPassword
  );

  void deleteUserByIdentifier(final UUID identifier);

  void deleteUser(final User user);

  /**
   * @throws NullPointerException     If the given email address is null.
   * @throws IllegalArgumentException
   */
  boolean checkUserExistsByEmail(final String email);
}
