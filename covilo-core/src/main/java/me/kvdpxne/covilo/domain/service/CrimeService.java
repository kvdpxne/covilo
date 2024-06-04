package me.kvdpxne.covilo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.domain.exceptions.CategoryNotFoundException;
import me.kvdpxne.covilo.domain.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
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

  /**
   *
   */
  public Crime _createCrime(
    Crime crime
  ) {
    crime = this.crimeRepository.insertCrimeAndReturn(crime);

    assert null != crime
      : "";

    logger.atInfo()
      .setMessage("")
      .addArgument(crime)
      .log();

    return crime;
  }

  /**
   *
   */
  public Crime createCrime(
    final Crime crime
  ) throws CrimeAlreadyExistsException {
    Validation.check(crime);

    final var reporter = this.userService._getUserByIdentifier(
      crime.getReporter().getIdentifier()
    );

    final var categories = crime.getCategories()
      .stream()
      .map(it -> this.systematizationService.getCategoryByName(it.getName()))
      .toList();

    //
    final var builder = crime.toBuilder();

    if (crime.isNew()) {
      builder.withRandomIdentifier();
    } else {

      final var identifier = crime.getIdentifier();

      if (this._checkCrimeExistsByIdentifier(identifier)) {
        throw new CrimeAlreadyExistsException(
          STR."The crime model with the identifier \{identifier} already exists."
        );
      }
    }

    return _createCrime(
      builder.withCurrentDateAsCreatedDate()
        .withCategories(categories)
        .withReporter(reporter)
        .build()
    );
  }

  public void deleteCrimeByIdentifier(final String identifier) {
    this.crimeRepository.deleteCrimeByIdentifier(identifier);
  }
}
