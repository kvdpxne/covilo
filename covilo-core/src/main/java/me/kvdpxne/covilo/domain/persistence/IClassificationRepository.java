package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Classification;

public interface IClassificationRepository {

  Classification findClassificationByIdentifierOrNull(final UUID identifier);

  Classification findClassificationByNameOrNull(final String name);
}
