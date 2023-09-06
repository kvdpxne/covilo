package me.kvdpxne.covilo.domain.service;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.CrimeLifecycleUseCase;
import me.kvdpxne.covilo.domain.exception.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;

@Slf4j
@RequiredArgsConstructor
public class CrimeLifecycleService implements CrimeLifecycleUseCase {

  private final CrimeRepository crimeRepository;

  @Override
  public Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException {
    return this.crimeRepository.findById(identifier)
      .orElseThrow(CrimeNotFoundException::new);
  }

  @Override
  public Crime createCrime(final Crime crime) throws CrimeAlreadyExistsException {
    Objects.requireNonNull(crime);

    final var identifier = crime.getIdentifier();
//    if (null != identifier) {
      //
      //
      if (this.crimeRepository.existsById(identifier)) {
        throw new CrimeAlreadyExistsException("");
      }
//    }

    final Crime created = this.crimeRepository.save(crime);
    logger.debug("Created crime: {}", crime);

    return created;
  }
}
