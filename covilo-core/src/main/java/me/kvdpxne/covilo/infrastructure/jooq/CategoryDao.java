package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.pagination.BasePage;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.records.CategoryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.CATEGORY;

/**
 * DAO implementation for handling categories using jOOQ.
 * <br>
 * This class interacts with the database using jOOQ {@link DSLContext} to
 * perform CRUD operations on categories.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class CategoryDao
  implements CategoryRepository {

  /**
   * The DSL context for jOOQ queries.
   */
  private final DSLContext ctx;

  /**
   * Converts a {@link CategoryRecord} to a {@link Category} object.
   *
   * @param categoryRecord The {@link CategoryRecord} to convert.
   * @return The corresponding {@link Category} object.
   */
  static Category toCategory(
    final CategoryRecord categoryRecord
  ) {
    if (null == categoryRecord) {
      return null;
    }

    return new Category(
      categoryRecord.getIdentifier(),
      categoryRecord.getName(),
      categoryRecord.getClassificationIdentifier()
    );
  }

  @Override
  public long countCategories() {
    return this.ctx.fetchCount(CATEGORY);
  }

  @Override
  public boolean existsCategoryByIdentifier(
    final UUID identifier
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
    return new BasePage<>(
      this.ctx.select(CATEGORY.fields())
        .from(CATEGORY)
        .limit(pageable.getSize())
        .offset(pageable.getOffset())
        .fetchInto(CATEGORY)
        .map(CategoryDao::toCategory),
      pageable,
      this.countCategories()
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
    return this.ctx.select(CATEGORY.fields())
      .from(CATEGORY)
      .where(condition)
      .fetchOptionalInto(CATEGORY)
      .map(CategoryDao::toCategory);
  }

  @Override
  public Optional<Category> findCategoryByIdentifier(
    final UUID identifier
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

  @Override
  public void insertCategories(
    final Collection<Category> categories
  ) {
    // noinspection preview
    categories.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(listableCategories -> {
        var insert = this.ctx.insertInto(
          CATEGORY,
          CATEGORY.IDENTIFIER,
          CATEGORY.NAME,
          CATEGORY.CLASSIFICATION_IDENTIFIER
        );
        for (final var category : listableCategories) {
          insert = insert.values(
            category.identifier(),
            category.name(),
            category.classificationIdentifier()
          );
        }
        insert.execute();
      });
  }

  @Override
  public void insertCategory(
    final Category category
  ) {
    this.ctx.insertInto(CATEGORY)
      .set(CATEGORY.IDENTIFIER, category.identifier())
      .set(CATEGORY.NAME, category.name())
      .execute();
  }

  @Override
  public Category insertCategoryAndReturn(
    final Category category
  ) {
    return CategoryDao.toCategory(
      this.ctx.insertInto(CATEGORY)
        .set(CATEGORY.IDENTIFIER, category.identifier())
        .set(CATEGORY.NAME, category.name())
        .set(CATEGORY.CLASSIFICATION_IDENTIFIER, category.classificationIdentifier())
        .returning(CATEGORY.fields())
        .fetchOneInto(CATEGORY)
    );
  }

  @Override
  public void updateCategory(
    final Category category
  ) {
    this.ctx.update(CATEGORY)
      .set(CATEGORY.NAME, category.name())
      .set(CATEGORY.CLASSIFICATION_IDENTIFIER, category.classificationIdentifier())
      .execute();
  }

  @Override
  public Category updateCategoryAndReturn(
    final Category category
  ) {
    return CategoryDao.toCategory(
      this.ctx.update(CATEGORY)
        .set(CATEGORY.NAME, category.name())
        .set(CATEGORY.CLASSIFICATION_IDENTIFIER, category.classificationIdentifier())
        .returning(CATEGORY.fields())
        .fetchOneInto(CATEGORY)
    );
  }

  @Override
  public boolean deleteCategoryByIdentifier(
    final UUID identifier
  ) {
    return 0 < this.ctx.deleteFrom(CATEGORY)
      .where(CATEGORY.IDENTIFIER.eq(identifier))
      .execute();
  }
}
