package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Repository interface for managing users.
 */
public interface UserRepository {

  /**
   * Retrieves the total count of users.
   *
   * @return The total count of users.
   */
  long countUsers();

  /**
   * Deletes a user by their identifier.
   *
   * @param identifier The identifier of the user to delete.
   * @return true if the user was deleted successfully, false otherwise.
   */
  void deleteUserByIdentifier(
    final String identifier
  );

  /**
   *
   */
  void deleteUsersByIdentifiers(
    final Collection<String> identifiers
  );

  /**
   * Checks if a user with the specified identifier exists.
   *
   * @param identifier The identifier of the user.
   * @return true if the user exists, false otherwise.
   */
  boolean existsUserByIdentifier(
    final String identifier
  );

  /**
   * Checks if a user with the specified email exists.
   *
   * @param email The email of the user.
   * @return true if the user exists, false otherwise.
   */
  boolean existsUserByEmail(
    final String email
  );

  /**
   * Retrieves a page of users.
   *
   * @param pageable The pagination information.
   * @return A page containing users.
   */
  Page<User> findUsers(
    final Pageable pageable
  );

  /**
   * Retrieves a user by their identifier.
   *
   * @param identifier The identifier of the user.
   * @return An optional containing the user, or empty if not found.
   */
  Optional<User> findUserByIdentifier(
    final String identifier
  );

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user.
   * @return An optional containing the user, or empty if not found.
   */
  Optional<User> findUserByEmail(
    final String email
  );

  /**
   * Inserts a new user into the repository.
   *
   * @param user The user to insert.
   */
  void insertUser(
    final User user
  );

  /**
   * Inserts a collection of users into the repository.
   *
   * @param users The collection of users to insert.
   */
  void insertUsers(
    final Collection<User> users
  );

  /**
   * Inserts a single user into the repository.
   *
   * @param user The user to insert.
   */
  User insertUserAndReturn(
    final User user
  );

  /**
   * Updates an existing user in the repository.
   *
   * @param user The user to update.
   */
  void updateUser(
    final User user
  );

  /**
   *
   */
  void updateUsers(
    final Collection<User> users
  );

  /**
   *
   */
  User updateUserAndReturn(
    final User user
  );
}
