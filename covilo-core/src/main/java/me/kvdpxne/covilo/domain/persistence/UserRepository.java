package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.User;

public interface UserRepository {

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param identifier The unique identifier of the user.
   * @return The user with the specified identifier, or null if no user i
   * found.
   */
  User findUserByIdentifierOrNull(final UUID identifier);

  /**
   * Retrieves a user by their email address.
   *
   * @param email The email address of the user.
   * @return The user with the specified email address, or null if no user is
   * found.
   */
  User findUserByEmailOrNull(final String email);

  User insert(final User user);

  /**
   *
   */
  boolean updateLastModifiedDateByIdentifier(
    final UUID identifier
  );

  /**
   * Sends a request to change the email address to the one provided for the
   * provided user identifier.
   *
   * @param identifier A unique user identifier.
   * @param email      email address.
   */
  boolean updateEmailByIdentifier(
    final UUID identifier,
    final String email
  );

  boolean updatePasswordByIdentifier(
    final UUID identifier,
    final String password
  );

  void deleteUserByIdentifier(final UUID identifier);

  boolean existsUserByIdentifier(final UUID identifier);

  boolean existsUserByEmail(final String email);
}
