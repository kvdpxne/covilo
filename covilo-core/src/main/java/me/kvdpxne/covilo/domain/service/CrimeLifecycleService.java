package me.kvdpxne.covilo.domain.service;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.ICrimeLifecycleService;
import me.kvdpxne.covilo.application.exception.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.application.exception.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.ICrimeRepository;

@Slf4j
@RequiredArgsConstructor
public class CrimeLifecycleService implements ICrimeLifecycleService {

  private final ICrimeRepository crimeRepository;

  @Override
  public Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException {
    final Crime crime = this.crimeRepository.findCrimeByIdentifierOrNull(identifier);
    if (null == crime) {
      throw new CrimeNotFoundException(
        "",
        identifier
      );
    }
    return crime;
  }

  @Override
  public Crime createCrime(final Crime crime) throws CrimeAlreadyExistsException {
    Objects.requireNonNull(crime);

    final var identifier = crime.identifier();
//    if (null != identifier) {
      //
      //
      if (this.crimeRepository.existsCrimeByIdentifier(identifier)) {
        throw new CrimeAlreadyExistsException("");
      }
//    }

    final Crime inserted = this.crimeRepository.insertCrime(crime);
    logger.debug("Created crime: {}", inserted);

    return inserted;
  }
}
