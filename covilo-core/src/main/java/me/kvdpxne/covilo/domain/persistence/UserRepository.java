package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

  Optional<User> findByEmail(final String email);
}
