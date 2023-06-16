package me.kvdpxne.covilo.application;

import me.kvdpxne.covilo.domain.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.domain.model.User;

public interface UserLifecycleUseCase {

  User createUser(final User user) throws UserAlreadyExistsException;
}
