package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Crime;

public interface CrimeRepository {

  Crime findCrimeByIdentifierOrNull(final UUID identifier);
}
