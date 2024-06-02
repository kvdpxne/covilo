package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.infrastructure.uid.Uid;
import me.kvdpxne.covilo.shared.Validation;

/**
 *
 */
@Slf4j
@RequiredArgsConstructor
public final class CrimeService {

  /**
   * Service for managing systematization data.
   */
  private final SystematizationService systematizationService;

  /**
   * Service for managing user data.
   */
  private final UserService userService;

  /**
   * Repository for managing crime data.
   */
  private final CrimeRepository crimeRepository;

  /**
   * Counts the total number of crimes.
   *
   * @return The total number of crimes.
   */
  public long countCrimes() {
    return Math.absExact(
      this.crimeRepository.countCrimes()
    );
  }

  public boolean _checkCrimeExistsByIdentifier(
    final String identifier
  ) {
    return this.crimeRepository.existsCrimeByIdentifier(identifier);
  }

  private String validCrimeIdentifier(
    final String identifier
  ) {
    return Validation.check(
      identifier,
      () -> ""
    );
  }

  private Crime validCrime(
    final Crime crime
  ) {
    return Validation.check(
      crime,
      () -> ""
    );
  }

  public boolean checkCrimeExistsByIdentifier(
    final String identifier
  ) {
    return this._checkCrimeExistsByIdentifier(
      this.validCrimeIdentifier(identifier)
    );
  }

  public boolean checkCrimeExists(
    final Crime crime
  ) {
    return this.checkCrimeExistsByIdentifier(
      this.validCrime(crime).getIdentifier()
    );
  }

  public Page<Crime> getCrimes(
    final Pageable pageable
  ) {
    return this.crimeRepository.getCrimes(pageable);
  }

  public Crime _getCrimeByIdentifier(
    final String identifier
  ) {
    return this.crimeRepository
      .findCrimeByIdentifier(identifier)
      .orElseThrow(() -> new CrimeNotFoundException(
        STR.""
      ));
  }

  public Crime getCrimeByIdentifier(
    final String identifier
  ) {
    return this._getCrimeByIdentifier(
      this.validCrimeIdentifier(identifier)
    );
  }


  public Crime createCrime(
    final Crime crime
  ) throws CrimeAlreadyExistsException {
    Validation.check(crime);

    var result = this.crimeRepository.insertCrimeAndReturn(
      Crime.builder()
        .withIdentifier(Uid.next(crime, it -> {
          //
          if (this._checkCrimeExistsByIdentifier(it)) {
            throw new CrimeAlreadyExistsException(
              STR."The crime model with the identifier \{it} already exists."
            );
          }
        }))
        .build()
    );


    logger.atDebug()
      .setMessage("Created crime: {}")
      .addArgument(result)
      .log();

    return result;
  }


  public void deleteCrimeByIdentifier(final String identifier) {
    this.crimeRepository.deleteCrimeByIdentifier(identifier);
  }
}
