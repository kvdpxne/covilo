package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.City;

public interface CityRepository {

  City findByIdentifierOrNull(final UUID identifier);
}
