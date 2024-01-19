package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;
import me.kvdpxne.covilo.domain.model.Classification;

public interface ClassificationRepository {

  Stream<Classification> getAll();

  Classification findClassificationByIdentifierOrNull(final UUID identifier);

  Classification findClassificationByNameOrNull(final String name);
}
