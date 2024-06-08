package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Repository interface for managing crime-related operations.
 */
public interface CrimeRepository {

  /**
   * Counts the total number of crime categories.
   *
   * @return The total number of crime categories.
   */
  long countCrimeCategories();

  /**
   * Counts the total number of crimes.
   *
   * @return The total number of crimes.
   */
  long countCrimes();

  /**
   * Deletes all crime categories associated with the given crime identifier.
   *
   * @param identifier The unique identifier of the crime.
   */
  void deleteCrimeCategoriesByIdentifier(
    final String identifier
  );

  /**
   * Deletes a crime by its unique identifier.
   *
   * @param identifier The unique identifier of the crime to delete.
   */
  void deleteCrimeByIdentifier(
    final String identifier)
  ;

  /**
   * Checks if a crime with the given identifier exists.
   *
   * @param identifier The unique identifier of the crime to check.
   * @return {@code true} if a crime with the given identifier exists, {@code false} otherwise.
   */
  boolean existsCrimeByIdentifier(
    final String identifier
  );

  /**
   * Retrieves a page of crimes.
   *
   * @param pageable The pagination information.
   * @return A page containing crimes.
   */
  Page<Crime> getCrimes(
    final Pageable pageable
  );

  /**
   *
   */
  Page<Crime> getLatestCrimes(
    final Pageable pageable
  );

  /**
   * Finds a crime by its unique identifier.
   *
   * @param identifier The unique identifier of the crime.
   * @return An optional containing the found crime, or empty if not found.
   */
  Optional<Crime> findCrimeByIdentifier(
    final String identifier
  );

  /**
   * Inserts a collection of crimes into the repository.
   *
   * @param crimes The collection of crimes to insert.
   */
  void insertCrimes(
    final Collection<Crime> crimes
  );

  /**
   * Inserts a crime into the repository.
   *
   * @param crime The crime to insert.
   */
  void insertCrime(
    final Crime crime
  );

  /**
   * Inserts a crime into the repository and returns the inserted crime.
   *
   * @param crime The crime to insert.
   * @return The inserted crime.
   */
  Crime insertCrimeAndReturn(
    final Crime crime
  );

  /**
   * Updates a collection of crimes in the repository.
   *
   * @param crimes The collection of crimes to update.
   */
  void updateCrimes(
    final Collection<Crime> crimes
  );

  /**
   * Updates an existing crime in the repository.
   *
   * @param crime The crime to update.
   */
  void updateCrime(
    final Crime crime
  );

  /**
   * Updates an existing crime in the repository and returns the updated crime.
   *
   * @param crime The crime to update.
   * @return The updated crime.
   */
  Crime updateCrimeAndReturn(
    final Crime crime
  );
}
