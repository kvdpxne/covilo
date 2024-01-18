package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.Region;

public interface RegionRepository {

  Book<Region> findProvinces(final BookAttributes attributes);

  Book<Region> findProvincesByCountryIdentifier(final UUID identifier, final BookAttributes attributes);

  Region findProvinceByIdentifierOrNull(final UUID identifier);

  Region findProvinceByNameOrNull(final String name);
}
