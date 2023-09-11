package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.User;

public interface UserRepository {

  User findUserByIdentifierOrNull(final UUID identifier);
  User findUserByEmailOrNull(final String email);

  User insertUser(final User user);

  boolean existsUserByIdentifier(final UUID identifier);
  boolean existsUserByEmail(final String email);
}
