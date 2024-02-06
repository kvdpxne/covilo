package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mappers.CrimePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repositories.JpaCrimeRepository;
import me.kvdpxne.covilo.shared.SpringPageRequestMapper;
import org.springframework.stereotype.Component;

/**
 * Data Access Object (DAO) for accessing Crime entities.
 */
@RequiredArgsConstructor
@Component
public final class CrimeDao
  implements CrimeRepository {

  /**
   * Repository for accessing Crime entities from the database.
   */
  private final JpaCrimeRepository crimeRepository;

  /**
   * Mapper for converting between CrimeEntity and Crime domain objects.
   */
  private final CrimePersistenceMapper crimeMapper;

  /**
   * Mapper for converting PageRange objects to Spring PageRequest objects.
   */
  private final SpringPageRequestMapper pageRequestMapper;

  @Override
  public List<Crime> getCrimes() {
    return this.crimeRepository.findAll()
      .stream()
      .map(this.crimeMapper::toDomain)
      .toList();
  }

  @Override
  public Iterable<Crime> getCrimes(
    final PageRange range
  ) {
    return this.crimeRepository.findAll(
      this.pageRequestMapper.toPageRequest(range)
    ).map(this.crimeMapper::toDomain);
  }

  @Override
  public Iterable<Crime> getCrimesByCriteria(
    final CrimeSearchCriteria criteria
  ) {
    return null;
  }

  @Override
  public Iterable<Crime> getCrimesByPlaceIdentifier(
    final UUID identifier,
    final PageRange range
  ) {
    return this.crimeRepository.findByPlace_Identifier(
      identifier,
      this.pageRequestMapper.toPageRequest(range)
    ).map(this.crimeMapper::toDomain);
  }

  @Override
  public Iterable<Crime> getCrimesByClassificationIdentifier(
    final UUID identifier,
    final PageRange range
  ) {
    return this.crimeRepository.findByClassification_Identifier(
      identifier,
      this.pageRequestMapper.toPageRequest(range)
    ).map(this.crimeMapper::toDomain);
  }

  @Override
  public Iterable<Crime> getCrimesByReporterIdentifier(
    final UUID identifier,
    final PageRange range
  ) {
    return this.crimeRepository.findByReporter_Identifier(
      identifier,
      this.pageRequestMapper.toPageRequest(range)
    ).map(this.crimeMapper::toDomain);
  }

  @Override
  public Optional<Crime> getCrimeByIdentifier(
    final UUID identifier
  ) {
    return this.crimeRepository.findById(identifier)
      .map(this.crimeMapper::toDomain);
  }

  @Override
  public Crime insertCrime(
    final Crime crime
  ) {
    return this.crimeMapper.toDomain(
      this.crimeRepository.save(
        this.crimeMapper.toDao(crime)
      )
    );
  }

  @Override
  public Crime updateCrime(
    final Crime crime
  ) {
    return this.crimeMapper.toDomain(
      this.crimeRepository.save(
        this.crimeMapper.partialUpdate(
          this.crimeRepository.getReferenceById(crime.identifier()),
          crime
        )
      )
    );
  }

  @Override
  public void deleteCrimeByIdentifier(
    final UUID identifier
  ) {
    this.crimeRepository.deleteById(identifier);
  }

  @Override
  public void deleteCrime(
    final Crime crime
  ) {
    this.deleteCrimeByIdentifier(crime.identifier());
  }

  @Override
  public boolean existsCrimeByIdentifier(
    final UUID identifier
  ) {
    return this.crimeRepository.existsById(identifier);
  }

  @Override
  public long countCrimes() {
    return this.crimeRepository.count();
  }
}
