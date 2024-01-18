package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.City;
import me.kvdpxne.covilo.domain.aggregation.Book;

public interface CityRepository {

  Book<City> findCities(final BookAttributes attributes);

  Book<City> findCitiesByProvinceIdentifier(final UUID identifier, final BookAttributes attributes);

  City findByIdentifierOrNull(final UUID identifier);
}
