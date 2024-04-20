package me.kvdpxne.covilo.domain.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Repository interface for managing categories.
 */
public interface CategoryRepository {

  /**
   * Counts the total number of categories.
   *
   * @return The total number of categories.
   */
  long countCategories();

  /**
   * Checks if a category with the given identifier exists.
   *
   * @param identifier The unique identifier of the category to check.
   * @return {@code true} if a category with the given identifier exists,
   * {@code false} otherwise.
   */
  boolean existsCategoryByIdentifier(
    final UUID identifier
  );

  /**
   * Finds categories based on pagination information.
   *
   * @param pageable Pagination information.
   * @return A page containing categories.
   */
  Page<Category> findCategories(
    final Pageable pageable
  );

  /**
   * Finds a category by its identifier.
   *
   * @param identifier The identifier of the category.
   * @return An optional containing the category if found, otherwise empty.
   */
  Optional<Category> findCategoryByIdentifier(
    final UUID identifier
  );

  /**
   * Finds a category by its name.
   *
   * @param name The name of the category.
   * @return An optional containing the category if found, otherwise empty.
   */
  Optional<Category> findCategoryByName(
    final String name
  );

  /**
   * Inserts the provided collection of categories into the repository.
   *
   * @param categories The collection of categories to insert.
   */
  void insertCategories(
    final Collection<Category> categories
  );

  /**
   * Inserts the provided category into the repository.
   *
   * @param category The category to insert.
   */
  void insertCategory(
    final Category category
  );

  /**
   * Inserts the provided category into the repository
   * and returns the inserted category object.
   *
   * @param category The category to insert.
   * @return The inserted category object.
   */
  Category insertCategoryAndReturn(
    final Category category
  );

  /**
   * Updates a category.
   *
   * @param category The category to update.
   */
  void updateCategory(
    final Category category
  );

  /**
   * Updates the provided category and returns the updated category object.
   *
   * @param category The category to update.
   * @return The updated category object.
   */
  Category updateCategoryAndReturn(
    final Category category
  );

  /**
   * Deletes a category by its identifier.
   *
   * @param identifier The identifier of the category to delete.
   * @return True if the category was deleted successfully, otherwise false.
   */
  boolean deleteCategoryByIdentifier(
    final UUID identifier
  );
}
