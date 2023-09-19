package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Province;

public interface IProvinceRepository {

  Book<Province> findProvinces(final BookAttributes attributes);

  Book<Province> findProvincesByCountryIdentifier(final UUID identifier, final BookAttributes attributes);

  Province findProvinceByIdentifierOrNull(final UUID identifier);

  Province findProvinceByNameOrNull(final String name);
}
