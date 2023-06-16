package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.UserLifecycleUseCase;
import me.kvdpxne.covilo.domain.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserLifecycleService
  implements UserLifecycleUseCase {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public User createUser(
    final User source
  ) throws UserAlreadyExistsException {
    // A unique user identifier.
    final var identifier = source.getIdentifier();

    // Checks if a user already exists in the database based on a unique
    // identifier.
    if (this.repository.existsById(identifier)) {
      throw new UserAlreadyExistsException(
        "User with identifier \"%s\" already exists.",
        identifier
      );
    }

    //
    final var encoded = source.toBuilder()
      .password(this.passwordEncoder.encode(source.getPassword()))
      .build();

    //
    final var created = this.repository.save(encoded);
    logger.info("Created user: {}", created);

    return created;
  }
}
