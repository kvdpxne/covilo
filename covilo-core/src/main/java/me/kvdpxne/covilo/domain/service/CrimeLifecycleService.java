package me.kvdpxne.covilo.domain.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.port.out.ICrimeLifecycleService;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.shared.Validation;

@Slf4j
@RequiredArgsConstructor
public class CrimeLifecycleService implements ICrimeLifecycleService {

  private final CrimeRepository crimeRepository;

  @Override
  public Iterable<Crime> getCrimes(
    final PageRange range
  ) {
    return this.crimeRepository.getCrimes(
      Validation.check(
        range,
        "The given PageRange cannot be null."
      )
    );
  }

  @Override
  public Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException {
    return this.crimeRepository.getCrimeByIdentifier(identifier)
      .orElseThrow(() -> new CrimeNotFoundException("", identifier));
  }

  @Override
  public Crime createCrime(final Crime crime) throws CrimeAlreadyExistsException {
    Objects.requireNonNull(
      crime,
      "The given parameter cannot be null."
    );
    var identifier = crime.identifier();
    var rebuilt = crime;
    if (null == identifier) {
      identifier = UUID.randomUUID();
      rebuilt = rebuilt.toBuilder()
        .identifier(identifier)
        .build();
    }
    if (null == rebuilt.createdDate()) {
      rebuilt = rebuilt.toBuilder()
        .createdDate(LocalDateTime.now())
        .build();
    }
    if (this.crimeRepository.existsCrimeByIdentifier(identifier)) {
      throw new CrimeAlreadyExistsException(
        String.format(
          "The crime model with the identifier \"%s\" already exists.",
          identifier
        )
      );
    }
    rebuilt = this.crimeRepository.insertCrime(rebuilt);
    logger.atDebug()
      .setMessage("Created crime: {}")
      .addArgument(rebuilt)
      .log();
    return rebuilt;
  }

  @Override
  public long countCrimes() {
    return this.crimeRepository.countCrimes();
  }
}
