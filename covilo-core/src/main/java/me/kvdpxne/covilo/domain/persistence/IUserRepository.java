package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.User;

public interface IUserRepository {

  User findUserByIdentifierOrNull(final UUID identifier);
  User findUserByEmailOrNull(final String email);

  User insertUser(final User user);

  void updateUserEmailByIdentifier(final UUID identifier, final String email);

  void updateUserPasswordByIdentifier(final UUID identifier, final String password);

  void deleteUserByIdentifier(final UUID identifier);

  boolean existsUserByIdentifier(final UUID identifier);
  boolean existsUserByEmail(final String email);
}
