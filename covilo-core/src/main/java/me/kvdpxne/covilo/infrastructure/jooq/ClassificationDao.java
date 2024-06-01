package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.records.ClassificationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.CLASSIFICATION;

/**
 * DAO implementation for handling classifications using jOOQ.
 * <br>
 * This class interacts with the database using jOOQ {@link DSLContext} to
 * perform CRUD operations on classifications.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class ClassificationDao
  implements ClassificationRepository {

  /**
   * The factory for creating configured pages.
   */
  private final ConfiguredPageFactory configuredPageFactory;

  /**
   * The DSL context for jOOQ queries.
   */
  private final DSLContext ctx;

  /**
   * Converts a {@link ClassificationRecord} to a {@link Classification} object.
   *
   * @param record The {@link ClassificationRecord} to convert.
   * @return The corresponding {@link Classification} object.
   */
  static Classification toClassification(
    final ClassificationRecord record
  ) {
    if (null == record) {
      return null;
    }
    return new Classification(
      record.getIdentifier(),
      record.getName()
    );
  }

  @Override
  public long countClassifications() {
    return this.ctx.fetchCount(CLASSIFICATION);
  }

  @Override
  public boolean existsClassificationByIdentifier(
    final String identifier
  ) {
    return this.ctx.fetchExists(
      CLASSIFICATION,
      CLASSIFICATION.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Page<Classification> getClassifications(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> this.ctx.selectFrom(CLASSIFICATION)
        .limit(pageable.getSize())
        .offset(pageable.getOffset())
        .fetchInto(CLASSIFICATION)
        .map(ClassificationDao::toClassification),
      this::countClassifications
    );
  }

  /**
   * Finds a classification by a given condition.
   *
   * @param condition The condition to match.
   * @return An optional containing the matching classification, if found.
   */
  private Optional<Classification> findClassificationBy(
    final Condition condition
  ) {
    return this.ctx.selectFrom(CLASSIFICATION)
      .where(condition)
      .fetchOptionalInto(CLASSIFICATION)
      .map(ClassificationDao::toClassification);
  }

  @Override
  public Optional<Classification> findClassificationByIdentifier(
    final String identifier
  ) {
    return this.findClassificationBy(
      CLASSIFICATION.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Optional<Classification> findClassificationByName(
    final String name
  ) {
    return this.findClassificationBy(
      CLASSIFICATION.NAME.eq(name.toLowerCase(Locale.ENGLISH))
    );
  }

  @Override
  public void insertClassifications(
    final Collection<Classification> classifications
  ) {
    // noinspection preview
    classifications.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(dividedClassifications -> {
        var insert = this.ctx.insertInto(
          CLASSIFICATION,
          CLASSIFICATION.IDENTIFIER,
          CLASSIFICATION.NAME
        );
        for (final var category : dividedClassifications) {
          insert = insert.values(
            category.getIdentifier(),
            category.getName()
          );
        }
        insert.execute();
      });
  }

  @Override
  public void insertClassification(
    final Classification classification
  ) {
    this.ctx.insertInto(CLASSIFICATION)
      .set(CLASSIFICATION.IDENTIFIER, classification.getIdentifier())
      .set(CLASSIFICATION.NAME, classification.getName())
      .execute();
  }

  @Override
  public Classification insertClassificationAndReturn(
    final Classification classification
  ) {
    return ClassificationDao.toClassification(
      this.ctx.insertInto(CLASSIFICATION)
        .set(CLASSIFICATION.IDENTIFIER, classification.getIdentifier())
        .set(CLASSIFICATION.NAME, classification.getName())
        .returning(CLASSIFICATION.fields())
        .fetchOneInto(CLASSIFICATION)
    );
  }

  @Override
  public void updateClassification(
    final Classification classification
  ) {
    this.ctx.update(CLASSIFICATION)
      .set(CLASSIFICATION.NAME, classification.getName())
      .where(CLASSIFICATION.IDENTIFIER.eq(classification.getIdentifier()))
      .execute();
  }

  @Override
  public Classification updateClassificationAndReturn(
    final Classification classification
  ) {
    return ClassificationDao.toClassification(
      this.ctx.update(CLASSIFICATION)
        .set(CLASSIFICATION.NAME, classification.getName())
        .where(CLASSIFICATION.IDENTIFIER.eq(classification.getIdentifier()))
        .returning(CLASSIFICATION.fields())
        .fetchOneInto(CLASSIFICATION)
    );
  }

  @Override
  public boolean deleteClassificationByIdentifier(
    final String identifier
  ) {
    return 0 < this.ctx.deleteFrom(CLASSIFICATION)
      .where(CLASSIFICATION.IDENTIFIER.eq(identifier))
      .execute();
  }
}
