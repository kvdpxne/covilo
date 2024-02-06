package me.kvdpxne.covilo.domain.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.paging.PageRange;
import me.kvdpxne.covilo.domain.persistence.specifications.CrimeSearchCriteria;

public interface CrimeRepository {

  /**
   * Retrieves all crimes.
   *
   * <p>
   * <b>Warning:</b>
   * This method should not be used in production environments as it may lead
   * to a StackOverflowException due to potential circular references or
   * excessively large datasets.
   * </p>
   *
   * @return An iterable of all crimes.
   */
  List<Crime> getCrimes();

  Iterable<Crime> getCrimes(
    final PageRange range
  );

  /**
   * Retrieves crimes based on search criteria.
   *
   * @param criteria The criteria to filter crimes.
   * @return An iterable of crimes matching the specified criteria.
   */
  Iterable<Crime> getCrimesByCriteria(final CrimeSearchCriteria criteria);

  /**
   * Retrieves crimes based on the identifier of the place where they occurred.
   *
   * @param identifier The identifier of the place.
   * @return An iterable of crimes that occurred at the specified place.
   */
  Iterable<Crime> getCrimesByPlaceIdentifier(final UUID identifier, final PageRange range);

  /**
   * Retrieves crimes based on the identifier of their classification.
   *
   * @param identifier The identifier of the classification.
   * @return An iterable of crimes with the specified classification.
   */
  Iterable<Crime> getCrimesByClassificationIdentifier(final UUID identifier, final PageRange range);

  /**
   * Retrieves crimes reported by a specific user.
   *
   * @param identifier The identifier of the reporter user.
   * @return An iterable of crimes reported by the specified user.
   */
  Iterable<Crime> getCrimesByReporterIdentifier(final UUID identifier, final PageRange range);

  /**
   * Retrieves a crime by its unique identifier.
   *
   * @param identifier The unique identifier of the crime.
   * @return An optional containing the crime if found, otherwise an empty optional.
   */
  Optional<Crime> getCrimeByIdentifier(final UUID identifier);

  /**
   * Inserts a new crime.
   *
   * @param crime The crime to insert.
   * @return The inserted crime.
   */
  Crime insertCrime(final Crime crime);

  /**
   * Updates an existing crime.
   *
   * @param crime The crime to update.
   * @return The updated crime.
   */
  Crime updateCrime(final Crime crime);

  /**
   * Deletes a crime by its unique identifier.
   *
   * @param identifier The unique identifier of the crime to delete.
   */
  void deleteCrimeByIdentifier(final UUID identifier);

  /**
   * Deletes a crime.
   *
   * @param crime The crime to delete.
   */
  void deleteCrime(final Crime crime);

  /**
   * Checks if a crime exists based on its unique identifier.
   *
   * @param identifier The unique identifier of the crime.
   * @return {@code true} if the crime exists, otherwise {@code false}.
   */
  boolean existsCrimeByIdentifier(final UUID identifier);

  /**
   * Counts the total number of crimes.
   *
   * @return The total number of crimes.
   */
  long countCrimes();
}
