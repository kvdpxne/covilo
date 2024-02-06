package me.kvdpxne.covilo.domain.port.out;

import java.util.UUID;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;

public interface ICrimeLifecycleService {

  Iterable<Crime> getCrimes(final PageRange range);

  /**
   * @throws NullPointerException
   * @throws CrimeNotFoundException
   */
  Crime getCrimeByIdentifier(final UUID identifier);

  /**
   * @throws NullPointerException
   * @throws CrimeAlreadyExistsException
   */
  Crime createCrime(final Crime crime);

  long countCrimes();
}
