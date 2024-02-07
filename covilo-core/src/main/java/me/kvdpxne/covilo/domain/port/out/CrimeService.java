package me.kvdpxne.covilo.domain.port.out;

import java.util.UUID;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;
import me.kvdpxne.covilo.presentation.CrimeSearchQuery;

public interface CrimeService {

  Iterable<Crime> getCrimes(
    final PageRange range
  );

  Iterable<Crime> getCrimes(
    final CrimeSearchCriteria criteria,
    final PageRange range
  );

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

  /**
   * @throws NullPointerException
   */
  boolean checkCrimeExistsByIdentifier(
    final UUID identifier
  );

  long countCrimes();
}
