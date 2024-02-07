package me.kvdpxne.covilo.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;
import me.kvdpxne.covilo.domain.port.out.CrimeService;
import me.kvdpxne.covilo.shared.Validation;

@Slf4j
@RequiredArgsConstructor
public final class CrimeLifecycleService
  implements CrimeService {

  private final CrimeRepository crimeRepository;

  @Override
  public Iterable<Crime> getCrimes(
    final PageRange range
  ) {
    return this.crimeRepository.getCrimes(
      null != range ? range : PageRange.DEFAULT_PAGE_RANGE
    );
  }

  @Override
  public Iterable<Crime> getCrimes(
    final CrimeSearchCriteria criteria,
    final PageRange range
  ) {
    if (null == criteria) {
      return this.getCrimes(range);
    }

    return null;
  }

  @Override
  public Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException {
    return this.crimeRepository.getCrimeByIdentifier(identifier)
      .orElseThrow(() -> new CrimeNotFoundException("", identifier));
  }

  @Override
  public Crime createCrime(
    final Crime crime
  ) throws CrimeAlreadyExistsException {
    Validation.check(crime);

    final var crimeBuilder = crime.toBuilder();

    if (crime.isNew()) {
      crimeBuilder.identifier(UUID.randomUUID());
    } else {
      var identifier = crime.identifier();
      if (this.crimeRepository.existsCrimeByIdentifier(identifier)) {
        throw new CrimeAlreadyExistsException(
          STR."The crime model with the identifier \{identifier} already exists."
        );
      }
    }

    if (!crime.wasCreated()) {
      if (crime.wasModified()) {
        throw new IllegalStateException("");
      }
      crimeBuilder.createdDate(LocalDateTime.now());
    }

    var result = crimeBuilder.build();
    result = this.crimeRepository.insertCrime(result);

    logger.atDebug()
      .setMessage("Created crime: {}")
      .addArgument(result)
      .log();

    return result;
  }

  @Override
  public boolean checkCrimeExistsByIdentifier(
    final UUID identifier
  ) {
    return this.crimeRepository.existsCrimeByIdentifier(
      Validation.check(identifier)
    );
  }

  @Override
  public long countCrimes() {
    return this.crimeRepository.countCrimes();
  }
}
