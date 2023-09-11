package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Country;

public interface CountryRepository {

  Country findCountryByIdentifierOrNull(final UUID identifier);

  Country findCountryByNameOrNull(final String name);
}
