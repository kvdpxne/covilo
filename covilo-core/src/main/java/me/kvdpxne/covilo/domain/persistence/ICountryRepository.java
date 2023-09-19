package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Country;

public interface ICountryRepository {

  Book<Country> findCountries(final BookAttributes attributes);
  Country findCountryByIdentifierOrNull(final UUID identifier);
  Country findCountryByNameOrNull(final String name);
  Country insertCountry(final Country country);
  void deleteCountryByIdentifier(final UUID identifier);
  void deleteCountry(final Country country);
}
