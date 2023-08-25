package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.PasswordEncodingUseCase;
import me.kvdpxne.covilo.application.UserLifecycleUseCase;
import me.kvdpxne.covilo.domain.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.UserNotFoundException;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.UserRepository;

/**
 * Service responsible for the user's life cycle.
 */
@Slf4j
@RequiredArgsConstructor
public final class UserLifecycleService
  implements UserLifecycleUseCase {

  private final UserRepository repository;

  private final PasswordEncodingUseCase passwordEncodingUseCase;

  @Override
  public User getUserByIdentifier(
    final UUID identifier
  ) throws UserNotFoundException {
    return this.repository.findById(identifier).orElseThrow(() -> new UserNotFoundException(""));
  }

  @Override
  public User getUserByEmail(
    final String email
  ) throws UserNotFoundException {
    return this.repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(""));
  }

  @Override
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

    final var email = source.getEmail();

    if (this.repository.existsByEmail(email)) {
      throw new UserAlreadyExistsException(
        "User with email \"%s\" already exists.",
        email
      );
    }

    //
    final var encoded = source.toBuilder()
      .password(this.passwordEncodingUseCase.encode(source.getPassword()))
      .build();

    //
    final var created = this.repository.save(encoded);
    logger.info("Created user: {}", created);

    return created;
  }
}
