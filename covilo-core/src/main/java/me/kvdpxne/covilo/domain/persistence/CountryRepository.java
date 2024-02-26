package me.kvdpxne.covilo.domain.persistence;

import java.util.List;
import java.util.UUID;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.Country;

public interface CountryRepository {

  List<Country> getAll();

  Book<Country> getAll(final BookAttributes attributes);

  Country findCountryByIdentifierOrNull(final UUID identifier);
  Country findCountryByNameOrNull(final String name);
  Country insertCountry(final Country country);
  void deleteCountryByIdentifier(final UUID identifier);
  void deleteCountry(final Country country);
}
