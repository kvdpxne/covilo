package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Crime;

public interface ICrimeRepository {

  Book<Crime> findCrimes(final BookAttributes attributes);

  Book<Crime> findCrimesByCityIdentifier(final UUID identifier, final BookAttributes attributes);

  Crime findCrimeByIdentifierOrNull(final UUID identifier);

  Crime insertCrime(final Crime crime);

  boolean existsCrimeByIdentifier(final UUID identifier);
}
