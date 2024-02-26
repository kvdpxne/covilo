package me.kvdpxne.covilo.domain.persistence;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import me.kvdpxne.covilo.domain.model.Classification;

public interface ClassificationRepository {

  Stream<Classification> getAll();

  Optional<Classification> findClassificationByIdentifier(final UUID identifier);

  Classification findClassificationByIdentifierOrNull(final UUID identifier);

  Classification findClassificationByNameOrNull(final String name);
}
