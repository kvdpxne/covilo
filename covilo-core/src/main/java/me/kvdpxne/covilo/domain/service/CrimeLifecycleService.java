package me.kvdpxne.covilo.domain.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.application.ICrimeLifecycleService;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
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
}
