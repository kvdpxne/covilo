package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.UpdateConditionStep;
import org.jooq.generated.tables.records.CategoryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.generated.Tables.CATEGORY;

/**
 * DAO implementation for handling categories using jOOQ.
 * <br>
 * This class interacts with the database using jOOQ {@link DSLContext} to
 * perform CRUD operations on categories.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class CategoryDao
  implements CategoryRepository {

  /**
   * The factory for creating configured pages.
   */
  private final ConfiguredPageFactory configuredPageFactory;

  /**
   * The DSL context for jOOQ queries.
   */
  private final DSLContext ctx;

  /**
   * Converts a {@link CategoryRecord} to a {@link Category} object.
   *
   * @param category       The {@link CategoryRecord} to convert.
   * @param classification The associated {@link Classification} object.
   * @return The corresponding {@link Category} object.
   */
  static Category toCategory(
    final CategoryRecord category,
    final Classification classification
  ) {
    if (null == category) {
      return null;
    }

    return Category.builder()
      .withIdentifier(category.getIdentifier())
      .withName(category.getName())
      .withClassification(classification)
      .build();
  }

  /**
   * Finds a {@link Category} instance by its identifier and returns it, or
   * {@code null} if not found.
   * <p>
   * This method retrieves the raw {@link Category} record from the database
   * using the provided identifier. It then retrieves the associated
   * {@link Classification} object (if any) using the
   * {@link ClassificationDao#findClassificationByIdentifierOrNull(DSLContext,
   * String)} method. Finally, it combines the raw category data with the
   * retrieved classification to construct and return a full {@link Category}
   * object.
   * </p>
   *
   * @param context    A {@link DSLContext} instance used to interact with the
   *                   database.
   * @param identifier The unique identifier of the {@link Category} to find.
   * @return A {@link Category} instance with the matching identifier, or
   * {@code null} if not found.
   */
  static Category findCategoryByIdentifierOrNull(
    final DSLContext context,
    final String identifier
  ) {
    // Fetch raw category record
    final var rawCategory = context.selectFrom(CATEGORY)
      .where(CATEGORY.IDENTIFIER.eq(identifier))
      .fetchOneInto(CATEGORY);

    // Return null if category not found
    if (null == rawCategory) {
      return null;
    }

    // Find associated classification (if any)
    final var classification =
      ClassificationDao.findClassificationByIdentifierOrNull(
        context,
        rawCategory.getIdentifier()
      );

    // Combine data and return full category
    return CategoryDao.toCategory(
      rawCategory,
      classification
    );
  }

  /**
   * Converts a {@link CategoryRecord} to a {@link Category} object using the
   * provided {@link DSLContext}.
   *
   * @param context  The DSL context for jOOQ queries.
   * @param category The {@link CategoryRecord} to convert.
   * @return The corresponding {@link Category} object.
   */
  static Category toCategory(
    final DSLContext context,
    final CategoryRecord category
  ) {
    final var classification =
      ClassificationDao.findClassificationByIdentifierOrNull(context, category.getIdentifier());

    return CategoryDao.toCategory(
      category,
      classification
    );
  }

  /**
   * Converts a {@link CategoryRecord} to a {@link Category} object using the
   * instance's DSL context.
   *
   * @param category The {@link CategoryRecord} to convert.
   * @return The corresponding {@link Category} object.
   */
  protected Category toCategory(
    final CategoryRecord category
  ) {
    return CategoryDao.toCategory(this.ctx, category);
  }

  @Override
  public long countCategories() {
    return this.ctx.fetchCount(CATEGORY);
  }

  @Override
  public void deleteCategoryByIdentifier(
    final String identifier
  ) {
    this.ctx.deleteFrom(CATEGORY)
      .where(CATEGORY.IDENTIFIER.eq(identifier))
      .execute();
  }

  /**
   * Deletes all {@link Category} records from the database.
   */
  void deleteAllCategories() {
    this.ctx.deleteFrom(CATEGORY).execute();
  }

  @Override
  public boolean existsCategoryByIdentifier(
    final String identifier
  ) {
    return this.ctx.fetchExists(
      CATEGORY,
      CATEGORY.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Page<Category> findCategories(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> this.ctx.select(CATEGORY.fields())
        .from(CATEGORY)
        .limit(pageable.getSize())
        .offset(pageable.getOffset())
        .fetchInto(CATEGORY)
        .map(this::toCategory),
      this::countCategories
    );
  }

  /**
   * Finds a category by a given condition.
   *
   * @param condition The condition to match.
   * @return An optional containing the matching category, if found.
   */
  private Optional<Category> findCategoryBy(
    final Condition condition
  ) {
    return this.ctx.selectFrom(CATEGORY)
      .where(condition)
      .fetchOptionalInto(CATEGORY)
      .map(this::toCategory);
  }

  @Override
  public Optional<Category> findCategoryByIdentifier(
    final String identifier
  ) {
    return this.findCategoryBy(
      CATEGORY.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Optional<Category> findCategoryByName(
    final String name
  ) {
    return this.findCategoryBy(
      CATEGORY.NAME.eq(name)
    );
  }

  @Transactional
  @Override
  public void insertCategories(
    final Collection<Category> categories
  ) {
    if (categories.isEmpty()) {
      return;
    }

    // Create a base insert step specifying the table and columns
    final var insertStep = this.ctx.insertInto(
      CATEGORY,
      CATEGORY.IDENTIFIER,
      CATEGORY.NAME,
      CATEGORY.CLASSIFICATION_IDENTIFIER
    ).values(
      // Placeholder values will be replaced during batch binding
      (String) null,
      null,
      null
    );

    // Process categories in chunks to improve performance
    categories.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(chunk -> {
        // Create a batch operation for efficient insertion
        final var batch = this.ctx.batch(insertStep);

        // Loop through each category in the chunk and bind actual values
        // from the category object to the insert step
        chunk.forEach(step -> {
          // noinspection ResultOfMethodCallIgnored
          batch.bind(
            step.getIdentifier(),
            step.getName(),
            step.getClassificationIdentifier()
          );
        });

        // Execute the batch insert operation
        batch.execute();
      });
  }


  protected InsertSetMoreStep<CategoryRecord> insertCategoryStep(
    final Category category
  ) {
    return this.ctx.insertInto(CATEGORY)
      .set(CATEGORY.IDENTIFIER, category.getIdentifier())
      .set(CATEGORY.NAME, category.getName())
      .set(CATEGORY.CLASSIFICATION_IDENTIFIER, category.getClassificationIdentifier());
  }

  @Override
  public void insertCategory(
    final Category category
  ) {
    this.insertCategoryStep(category).execute();
  }

  @Override
  public Category insertCategoryAndReturn(
    final Category category
  ) {
    final var rawCategory = this.insertCategoryStep(category)
      .returning(CATEGORY.fields())
      .fetchOneInto(CATEGORY);

    return null != rawCategory
      ? this.toCategory(rawCategory)
      : null;
  }


  protected UpdateConditionStep<CategoryRecord> updateCategoryStep(
    final Category category
  ) {
    return this.ctx.update(CATEGORY)
      .set(CATEGORY.NAME, category.getName())
      .set(CATEGORY.CLASSIFICATION_IDENTIFIER, category.getClassificationIdentifier())
      .where(CATEGORY.IDENTIFIER.eq(category.getIdentifier()));
  }

  @Transactional
  @Override
  public void updateCategories(
    final Collection<Category> categories
  ) {
    categories.forEach(it -> this.updateCategoryStep(it).execute());
  }

  @Override
  public void updateCategory(
    final Category category
  ) {
    this.updateCategoryStep(category).execute();
  }

  @Override
  public Category updateCategoryAndReturn(
    final Category category
  ) {
    final var rawCategory = this.updateCategoryStep(category)
      .returning(CATEGORY.fields())
      .fetchOneInto(CATEGORY);

    return null != rawCategory
      ? this.toCategory(rawCategory)
      : null;
  }
}
