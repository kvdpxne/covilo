package me.kvdpxne.covilo.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kvdpxne.covilo.domain.exceptions.CategoryAlreadyExistsException;
import me.kvdpxne.covilo.domain.exceptions.ClassificationAlreadyExistsException;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.exceptions.CategoryNotFoundException;
import me.kvdpxne.covilo.domain.exceptions.ClassificationNotFoundException;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.shared.Validation;

/**
 * Service class providing methods for retrieving classifications and
 * categories.
 */
@Slf4j
@RequiredArgsConstructor
public final class SystematizationService {

  /**
   * The repository for managing classifications.
   */
  private final ClassificationRepository classificationRepository;

  /**
   * The repository for managing categories.
   */
  private final CategoryRepository categoryRepository;

  /**
   * Checks if a classification exists in the repository based on its
   * identifier.
   *
   * @param identifier The identifier of the classification to check.
   * @return {@code true} if the classification exists, {@code false} otherwise.
   */
  public boolean _checkClassificationExistsByIdentifier(
    final UUID identifier
  ) {
    return this.classificationRepository.existsClassificationByIdentifier(
      identifier
    );
  }

  /**
   * Validates the provided classification identifier, ensuring it is not
   * {@code null}.
   * <br>
   * If the identifier is {@code null}, a {@link NullPointerException} is
   * thrown.
   *
   * @param identifier The classification identifier to validate.
   * @return The validated classification identifier.
   * @throws NullPointerException If the provided classification identifier
   *                              is {@code null}.
   */
  private UUID validClassificationIdentifier(
    final UUID identifier
  ) {
    return Validation.check(
      identifier,
      () -> "The provided classification identifier cannot be null."
    );
  }

  /**
   * Validates the provided classification object, ensuring it is not {@code
   * null}.
   * <br>
   * If the classification object is {@code null}, a
   * {@link NullPointerException} is thrown.
   *
   * @param classification The classification object to validate.
   * @return The validated classification object.
   * @throws NullPointerException If the provided classification object is
   *                              {@code null}.
   */
  private Classification validClassification(
    final Classification classification
  ) {
    return Validation.check(
      classification,
      () -> "The provided classification cannot be null."
    );
  }

  /**
   * Checks if a classification exists based on its identifier.
   *
   * @param identifier The identifier of the classification to check.
   * @return {@code true} if the classification exists, {@code false} otherwise.
   * @throws NullPointerException If the provided classification identifier
   *                              is {@code null}.
   * @see #_checkClassificationExistsByIdentifier(UUID)
   */
  public boolean checkClassificationExistsByIdentifier(
    final UUID identifier
  ) {
    return this._checkClassificationExistsByIdentifier(
      this.validClassificationIdentifier(identifier)
    );
  }

  /**
   * Checks if a classification exists based on the provided classification
   * object.
   *
   * @param classification The classification object to check.
   * @return {@code true} if the classification exists, {@code false} otherwise.
   * @throws NullPointerException If the provided classification is {@code
   *                              null}.
   * @see #checkClassificationExistsByIdentifier(UUID)
   */
  public boolean checkClassificationExists(
    final Classification classification
  ) {
    return this.checkClassificationExistsByIdentifier(
      this.validClassification(classification).getIdentifier()
    );
  }

  /**
   * Checks if a category exists in the repository based on its identifier.
   *
   * @param identifier The identifier of the category to check.
   * @return {@code true} if the category exists, {@code false} otherwise.
   */
  public boolean _checkCategoryExistsByIdentifier(
    final UUID identifier
  ) {
    return this.categoryRepository.existsCategoryByIdentifier(
      identifier
    );
  }

  /**
   * Validates the provided category identifier, ensuring it is not {@code
   * null}.
   * <br>
   * If the identifier is {@code null}, a {@link NullPointerException}
   * is thrown.
   *
   * @param identifier The category identifier to validate.
   * @return The validated category identifier.
   * @throws NullPointerException If the provided category identifier is
   *                              {@code null}.
   */
  private UUID validCategoryIdentifier(
    final UUID identifier
  ) {
    return Validation.check(
      identifier,
      () -> "The provided category identifier cannot be null."
    );
  }

  /**
   * Validates the provided category object, ensuring it is not {@code null}.
   * <br>
   * If the category object is {@code null}, a {@link NullPointerException} is
   * thrown.
   *
   * @param category The category object to validate.
   * @return The validated category object.
   * @throws NullPointerException If the provided category object is {@code
   *                              null}.
   */
  private Category validCategory(
    final Category category
  ) {
    return Validation.check(
      category,
      () -> "The provided category cannot be null."
    );
  }

  /**
   * Checks if a category exists based on its identifier.
   *
   * @param identifier The identifier of the category to check.
   * @return {@code true} if the category exists, {@code false} otherwise.
   * @throws NullPointerException If the provided category identifier is
   *                              {@code null}.
   * @see #_checkClassificationExistsByIdentifier(UUID)
   */
  public boolean checkCategoryExistsByIdentifier(
    final UUID identifier
  ) {
    return this._checkCategoryExistsByIdentifier(
      this.validCategoryIdentifier(identifier)
    );
  }

  /**
   * Checks if a category exists based on the provided category object.
   *
   * @param category The category object to check.
   * @return {@code true} if the category exists, {@code false} otherwise.
   * @throws NullPointerException If the provided category is {@code null}.
   * @see #checkCategoryExistsByIdentifier(UUID)
   */
  public boolean checkCategoryExists(
    final Category category
  ) {
    return this.checkCategoryExistsByIdentifier(
      this.validCategory(category).getIdentifier()
    );
  }

  /**
   * Retrieves a page of classifications.
   *
   * @param pageable The pagination information.
   *                 If {@code null}, the default pageable configuration is
   *                 used.
   * @return A page containing classifications.
   */
  public Page<Classification> getClassifications(
    final Pageable pageable
  ) {
    return this.classificationRepository.getClassifications(
      pageable
    );
  }

  /**
   * Retrieves a page of categories.
   *
   * @param pageable The pagination information.
   *                 If {@code null}, the default pageable configuration is
   *                 used.
   * @return A page containing categories.
   */
  public Page<Category> getCategories(
    final Pageable pageable
  ) {
    return this.categoryRepository.findCategories(
      pageable
    );
  }

  /**
   * Retrieves a classification from the repository based on the provided
   * identifier.
   * <br>
   * If a classification with the specified identifier is found in the
   * repository, it is returned. Otherwise, a
   * {@link ClassificationNotFoundException} is thrown.
   *
   * @param identifier The identifier of the classification to retrieve.
   * @return The classification with the specified identifier.
   * @throws ClassificationNotFoundException If no classification record with
   *                                         the specified identifier is found.
   */
  public Classification _getClassificationByIdentifier(
    final UUID identifier
  ) {
    // noinspection preview
    return this.classificationRepository
      .findClassificationByIdentifier(identifier)
      .orElseThrow(() -> new ClassificationNotFoundException(
        STR."No classification record with identifier \{identifier} found.")
      );
  }

  /**
   * Retrieves a classification by its identifier.
   *
   * @param identifier The identifier of the classification.
   * @return The classification with the specified identifier.
   * @throws NullPointerException            If the provided identifier is
   *                                         {@code null}.
   * @throws ClassificationNotFoundException If the classification is not found.
   * @see #_getClassificationByIdentifier(UUID)
   */
  public Classification getClassificationByIdentifier(
    final UUID identifier
  ) {
    return this._getClassificationByIdentifier(
      this.validClassificationIdentifier(identifier)
    );
  }

  /**
   * Retrieves a category from the repository based on the provided identifier.
   * If a category with the specified identifier is found in the repository,
   * it is returned.
   * Otherwise, a {@link CategoryNotFoundException} is thrown.
   *
   * @param identifier The identifier of the category to retrieve.
   * @return The category with the specified identifier.
   * @throws CategoryNotFoundException If no category record with the
   *                                   specified identifier is found.
   */
  public Category _getCategoryByIdentifier(
    final UUID identifier
  ) {
    // noinspection preview
    return this.categoryRepository.findCategoryByIdentifier(identifier)
      .orElseThrow(() -> new CategoryNotFoundException(
        STR."No category record with identifier \{identifier} found."
      ));
  }

  /**
   * Retrieves a category by its identifier.
   *
   * @param identifier The identifier of the category.
   * @return The category with the specified identifier.
   * @throws NullPointerException      If the provided identifier is
   *                                   {@code null}.
   * @throws CategoryNotFoundException If the category is not found.
   * @see #_getCategoryByIdentifier(UUID)
   */
  public Category getCategoryByIdentifier(
    final UUID identifier
  ) {
    return this._getCategoryByIdentifier(
      this.validCategoryIdentifier(identifier)
    );
  }

  /**
   * Creates a new classification in the repository with the provided
   * information and returns the created classification.
   * <p>
   * This method inserts the provided classification into the database and
   * retrieves the newly created classification. It also ensures that the
   * inserted classification object is not null after insertion into the
   * database. If the classification object is unexpectedly null after
   * insertion, it indicates a failure in the insertion process.
   *
   * @param classification The classification object to create.
   * @return The newly created classification.
   * @throws AssertionError If the created classification object is
   *                        unexpectedly null after insertion into the database.
   */
  public Classification _createClassification(
    final Classification classification
  ) {
    // Insert the classification into the repository
    // and retrieve the newly created classification
    final var newClassification = this.classificationRepository
      .insertClassificationAndReturn(classification);

    /* Ensure that the new classification object is not null after insertion
     * into the database.
     *
     * If the classification object is unexpectedly null, it indicates a
     * failure in the insertion process.
     */
    assert null != newClassification
      : "The classification was not inserted into the database table.";

    // Log the creation of the new classification
    logger.atInfo()
      .setMessage("A new classification has been created: {}")
      .addArgument(newClassification)
      .log();

    // Return the newly created classification
    return newClassification;
  }

  /**
   * Creates a new classification based on the provided classification object.
   * <br>
   * Note: The provided classification object may differ from the one
   * returned after insertion into the database. For example, the returned
   * classification may have additional properties populated by the database.
   *
   * @param classification The classification object to create.
   * @return The newly created classification.
   * @throws NullPointerException     If the provided classification is
   *                                  {@code null}.
   * @throws IllegalArgumentException If the name of the classification is
   *                                  empty or contains only whitespaces.
   * @see #_createClassification(Classification)
   */
  public Classification createClassification(
    final Classification classification
  ) {
    // Validate that the provided classification is not null
    this.validClassification(classification);

    // Validate that the name of the classification is not empty
    Validation.empty(classification.getName());

    // Create a new builder for the classification
    var builder = Classification.builder();

    // If the classification is new, generate a random identifier
    if (classification.isNew()) {
      // Generate a random UUID for the new classification
      final var randomIdentifier = UUID.randomUUID();

      // Set the random identifier for the new classification
      builder.withIdentifier(randomIdentifier);

      // Log the assignment of the random identifier
      logger.atDebug()
        .setMessage("Assigned random identifier {} to the new classification.")
        .addArgument(randomIdentifier)
        .log();
    } else {
      // If the classification is not new, use the existing identifier
      final var identifier = classification.getIdentifier();

      // Check if a classification with the same identifier already exists
      if (this._checkClassificationExistsByIdentifier(identifier)) {
        throw new ClassificationAlreadyExistsException(
          "A classification with the same identifier already exists."
        );
      }

      // Set the identifier for the new classification to the existing one
      builder.withIdentifier(identifier);
    }

    return this._createClassification(
      builder.withName(classification.getLowerName())
        .build()
    );
  }

  /**
   * Creates a new category in the repository with the provided information and returns the created category.
   * <p>
   * This method inserts the provided category into the database and retrieves the newly created category.
   * It also ensures that the inserted category object is not null after insertion into the database.
   * If the category object is unexpectedly null after insertion, it indicates a failure in the insertion process.
   * </p>
   *
   * @param category The category object to create.
   * @return The newly created category.
   * @throws AssertionError If the created category object is unexpectedly null after insertion into the database.
   */
  public Category _createCategory(
    final Category category
  ) {
    // Insert the category into the repository
    // and retrieve the newly created category
    final var newCategory = this.categoryRepository
      .insertCategoryAndReturn(category);

    /* Ensure that the new category object is not null after insertion
     * into the database.
     *
     * If the category object is unexpectedly null, it indicates a
     * failure in the insertion process.
     */
    assert null != newCategory
      : "The category was not inserted into the database table.";

    // Log the creation of the new category
    logger.atInfo()
      .setMessage("A new category has been created: {}")
      .addArgument(newCategory)
      .log();

    // Return the newly created category
    return newCategory;
  }

  /**
   * Creates a new category based on the provided category object.
   *
   * <p>
   * Note: The provided category object may differ from the one returned
   * after insertion into the database. For example, the returned category
   * may have additional properties populated by the database.
   * </p>
   *
   * @param category The category object to create.
   * @return The newly created category.
   * @throws NullPointerException           If the provided category is
   *                                        {@code null}.
   * @throws IllegalArgumentException       If the name of the category is empty or
   *                                        contains only whitespaces.
   * @throws CategoryAlreadyExistsException If a category with the same
   *                                        identifier already exists and the
   *                                        category is new.
   * @see #_createCategory(Category)
   */
  public Category createCategory(
    final Category category
  ) {
    // Validate that the provided category is not null
    this.validCategory(category);

    // Validate that the name of the category is not empty
    Validation.empty(category.getName());

    // Create a new builder for the category
    var builder = Category.builder();

    // If the category is new, generate a random identifier
    if (category.isNew()) {
      // Generate a random UUID for the new category
      final var randomIdentifier = UUID.randomUUID();

      // Set the random identifier for the new category
      builder.withIdentifier(randomIdentifier);

      // Log the assignment of the random identifier
      logger.atDebug()
        .setMessage("Assigned random identifier {} to the new category.")
        .addArgument(randomIdentifier)
        .log();
    } else {
      // If the category is not new, use the existing identifier
      final var identifier = category.getIdentifier();

      // Check if a category with the same identifier already exists
      if (this._checkCategoryExistsByIdentifier(identifier)) {
        throw new CategoryAlreadyExistsException(
          "A category with the same identifier already exists."
        );
      }

      // Set the identifier for the new category to the existing one
      builder.withIdentifier(identifier);
    }

    return this._createCategory(
      builder.withName(category.getLowerName())
        .build()
    );
  }

  /**
   * Updates an existing classification in the repository
   * and returns the updated classification.
   *
   * @param classification The classification to update.
   * @return The updated classification.
   */
  public Classification _updateClassification(
    final Classification classification
  ) {
    // Update the classification in the repository
    // and retrieve the updated classification
    final var newClassification = this.classificationRepository
      .updateClassificationAndReturn(classification);

    // Log the update of the classification
    logger.atInfo()
      .setMessage("Classification updated: {}")
      .addArgument(newClassification)
      .log();

    // Return the updated classification
    return newClassification;
  }

  /**
   * Updates an existing classification in the repository with the provided
   * information and returns the updated classification.
   * <p>
   * This method first validates the provided classification to ensure it exists in the database and has a non-empty name.
   * If the provided classification is null, a {@link NullPointerException} is thrown.
   * If the provided classification is new (i.e., it does not exist in the database), an {@link IllegalArgumentException} is thrown.
   * If the name of the classification is empty or contains only whitespaces, an {@link IllegalArgumentException} is thrown.
   * </p>
   *
   * @param classification The classification object containing the updated information.
   * @return The updated classification.
   * @throws NullPointerException     If the provided classification is
   *                                  {@code null}.
   * @throws IllegalArgumentException If the provided classification is new,
   *                                  or if its name is empty or contains
   *                                  only whitespaces.
   * @see #_updateClassification(Classification)
   */
  public Classification updateClassification(
    final Classification classification
  ) {
    Validation.check(
      this.validClassification(classification).isNew(),
      "The provided classification does not exist in database."
    );

    //
    Validation.empty(classification.getName());

    return this._updateClassification(
      classification.toBuilder()
        .withName(classification.getLowerName())
        .build()
    );
  }

  /**
   * Updates an existing category in the repository and returns the updated
   * category.
   *
   * @param category The category to update.
   * @return The updated category.
   */
  public Category _updateCategory(
    final Category category
  ) {
    // Update the category in the repository and retrieve the updated category
    final var newCategory = this.categoryRepository
      .updateCategoryAndReturn(category);

    // Log the update of the category
    logger.atInfo()
      .setMessage("Category updated: {}")
      .addArgument(newCategory)
      .log();

    // Return the updated category
    return newCategory;
  }

  /**
   * Updates an existing category in the repository with the provided information and returns the updated category.
   * This method first validates the provided category
   * to ensure it exists in the database and has a non-empty name.
   * <p>
   * If the provided category is null, a {@link NullPointerException} is thrown.
   * <p>
   * If the provided category is new (i.e., it does not exist in the database),
   * an {@link IllegalArgumentException} is thrown.
   * <p>
   * If the name of the category is empty or contains only whitespaces,
   * an {@link IllegalArgumentException} is thrown.
   *
   * @param category The category object containing the updated information.
   * @return The updated category.
   * @throws NullPointerException     If the provided category is {@code null}.
   * @throws IllegalArgumentException If the provided category is new,
   *                                  or if its name is empty
   *                                  or contains only whitespaces.
   * @see #_updateCategory(Category)
   */
  public Category updateCategory(
    final Category category
  ) {
    Validation.check(
      this.validCategory(category).isNew(),
      "The provided category to update not exists in database because its has" +
        " null identifier."
    );

    Validation.empty(category.getName());

    return this._updateCategory(
      category.toBuilder()
        .withName(category.getLowerName())
        .build()
    );
  }

  /**
   * Deletes a classification from the repository based on the provided
   * identifier.
   *
   * @param identifier The identifier of the classification to delete.
   * @return {@code true} if the deletion was successful, otherwise {@code
   * false}.
   * @throws NullPointerException If the provided classification identifier
   *                              is {@code null}.
   */
  public boolean deleteClassificationByIdentifier(
    final UUID identifier
  ) {
    return this.classificationRepository.deleteClassificationByIdentifier(
      this.validClassificationIdentifier(identifier)
    );
  }

  /**
   * Deletes a category from the repository based on the provided identifier.
   *
   * @param identifier The identifier of the category to delete.
   * @return {@code true} if the deletion was successful, otherwise {@code
   * false}.
   * @throws NullPointerException If the provided category identifier is
   *                              {@code null}.
   */
  public boolean deleteCategoryByIdentifier(
    final UUID identifier
  ) {
    return this.categoryRepository.deleteCategoryByIdentifier(
      this.validCategoryIdentifier(identifier)
    );
  }
}
