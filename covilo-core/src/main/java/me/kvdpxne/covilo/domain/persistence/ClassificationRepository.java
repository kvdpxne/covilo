package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Represents a repository interface for managing classifications.
 * This interface provides methods for CRUD operations on Classification
 * entities.
 */
public interface ClassificationRepository {

  /**
   * Counts the total number of classifications.
   *
   * @return The total number of classifications.
   */
  long countClassifications();

  /**
   * Checks if a classification with the specified identifier exists.
   *
   * @param identifier The unique identifier of the classification to check.
   * @return {@code true} if a classification with the specified identifier
   * exists, otherwise {@code false}.
   */
  boolean existsClassificationByIdentifier(
    final UUID identifier
  );

  /**
   * Retrieves a page of classifications.
   *
   * @param pageable The pagination information.
   * @return A page containing classifications.
   */
  Page<Classification> getClassifications(
    final Pageable pageable
  );

  /**
   * Finds a classification by its unique identifier.
   *
   * @param identifier The unique identifier of the classification.
   * @return An optional containing the found classification, or empty if not
   * found.
   */
  Optional<Classification> findClassificationByIdentifier(
    final UUID identifier
  );

  /**
   * Finds a classification by its name.
   *
   * @param name The name of the classification.
   * @return An optional containing the found classification, or empty if not
   * found.
   */
  Optional<Classification> findClassificationByName(
    final String name
  );

  /**
   * Inserts multiple classifications into the repository.
   *
   * @param classifications The classifications to insert.
   */
  void insertClassifications(
    final Collection<Classification> classifications
  );

  /**
   * Inserts a new classification into the repository.
   *
   * @param classification The classification to insert.
   */
  void insertClassification(
    final Classification classification
  );


  /**
   * Inserts a new classification into the repository and returns the
   * inserted classification.
   *
   * @param classification The classification to insert.
   * @return The inserted classification.
   */
  Classification insertClassificationAndReturn(
    final Classification classification
  );

  /**
   * Updates an existing classification in the repository.
   *
   * @param classification The classification to update.
   */
  void updateClassification(
    final Classification classification
  );

  /**
   * Updates an existing classification in the repository and returns the
   * updated classification.
   *
   * @param classification The classification to update.
   * @return The updated classification.
   */
  Classification updateClassificationAndReturn(
    final Classification classification
  );

  /**
   * Deletes a classification by its unique identifier.
   *
   * @param identifier The unique identifier of the classification to delete.
   * @return {@code true} if the deletion was successful, otherwise {@code
   * false}.
   */
  boolean deleteClassificationByIdentifier(
    final UUID identifier
  );
}
