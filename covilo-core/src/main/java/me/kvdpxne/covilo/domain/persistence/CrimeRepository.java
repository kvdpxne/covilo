package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

public interface CrimeRepository {

  /**
   * Counts the total number of crimes.
   *
   * @return The total number of crimes.
   */
  long countCrimes();

  /**
   * Checks if a crime with the given identifier exists.
   *
   * @param identifier The unique identifier of the crime to check.
   * @return {@code true} if a crime with the given identifier exists,
   * {@code false} otherwise.
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
   *
   */
  Crime insertCrimeAndReturn(
    final Crime crime
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
   * Deletes a crime by its unique identifier.
   *
   * @param identifier The unique identifier of the crime to delete.
   * @return {@code true} if the deletion was successful,otherwise
   * {@code false}.
   */
  boolean deleteCrimeByIdentifier(
    final String identifier
  );
}
