package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.Collection;
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
import org.jooq.InsertSetMoreStep;
import org.jooq.UpdateConditionStep;
import org.jooq.generated.tables.records.ClassificationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.CLASSIFICATION;
import static org.jooq.impl.DSL.lower;

/**
 * DAO implementation for handling classifications using jOOQ.
 * <br>
 * This class interacts with the database using jOOQ {@link DSLContext} to
 * perform CRUD operations on classifications.
 *
 * @since 0.1
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class ClassificationDao
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
   * Converts a {@link ClassificationRecord} to a {@link Classification}
   * object.
   *
   * @param classification The {@link ClassificationRecord} to convert.
   * @return The corresponding {@link Classification} object.
   */
  static Classification toClassification(
    final ClassificationRecord classification
  ) {
    if (null == classification) {
      return null;
    }

    return Classification.builder()
      .withIdentifier(classification.getIdentifier())
      .withName(classification.getName())
      .build();
  }

  /**
   * Finds a {@link Classification} object by its identifier and returns it, or
   * {@code null} if not found.
   *
   * @param context    A {@link DSLContext} object used to interact with the
   *                   database.
   * @param identifier The unique identifier of the {@link Classification}
   *                   object to find.
   * @return A {@link Classification} object with the matching identifier, or
   * {@code null} if not found.
   */
  static Classification findClassificationByIdentifierOrNull(
    final DSLContext context,
    final String identifier
  ) {
    // Fetch raw classification record
    final var rawClassification = context.selectFrom(CLASSIFICATION)
      .where(lower(CLASSIFICATION.IDENTIFIER).eq(identifier.toLowerCase()))
      .fetchOneInto(CLASSIFICATION);

    return ClassificationDao.toClassification(
      rawClassification
    );
  }

  @Override
  public long countClassifications() {
    return this.ctx.fetchCount(CLASSIFICATION);
  }

  @Override
  public void deleteClassificationByIdentifier(
    final String identifier
  ) {
    this.ctx.deleteFrom(CLASSIFICATION)
      .where(lower(CLASSIFICATION.IDENTIFIER).eq(identifier.toLowerCase()))
      .execute();
  }

  @Override
  public void deleteClassificationsByIdentifiers(
    final Collection<String> identifiers
  ) {
    if (null == identifiers || identifiers.isEmpty()) {
      return;
    }

    this.ctx.deleteFrom(CLASSIFICATION)
      .where(CLASSIFICATION.IDENTIFIER.in(identifiers))
      .execute();
  }

  /**
   * Deletes all {@link Classification} records from the database.
   */
  void deleteAllClassifications() {
    this.ctx.deleteFrom(CLASSIFICATION).execute();
  }

  @Override
  public boolean existsClassificationByIdentifier(
    final String identifier
  ) {
    return this.ctx.fetchExists(
      CLASSIFICATION,
      lower(CLASSIFICATION.IDENTIFIER).eq(identifier.toLowerCase())
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
      lower(CLASSIFICATION.IDENTIFIER).eq(identifier.toLowerCase())
    );
  }

  @Override
  public Optional<Classification> findClassificationByName(
    final String name
  ) {
    return this.findClassificationBy(
      lower(CLASSIFICATION.NAME).eq(name.toLowerCase())
    );
  }

  @Override
  public void insertClassifications(
    final Collection<Classification> classifications
  ) {
    if (null == classifications || classifications.isEmpty()) {
      return;
    }

    // Create a base insert step specifying the table and columns
    final var insertStep = this.ctx.insertInto(
      CLASSIFICATION,
      CLASSIFICATION.IDENTIFIER,
      CLASSIFICATION.NAME
    ).values(
      // Placeholder values will be replaced during batch binding
      (String) null,
      null
    );

    // Process classifications in chunks to improve performance
    classifications.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(chunk -> {
        // Create a batch operation for efficient insertion
        final var batch = this.ctx.batch(insertStep);

        // Loop through each classification in the chunk and bind actual
        // values from the classification object to the insert step
        chunk.forEach(step -> {
          // noinspection ResultOfMethodCallIgnored
          batch.bind(
            step.getIdentifier(),
            step.getName()
          );
        });

        // Execute the batch insert operation
        batch.execute();
      });
  }

  /**
   * Creates a step to insert a new {@link Classification} record into the
   * database.
   *
   * @param classification The {@link Classification} object containing the data
   *                       to be inserted.
   * @return An {@link InsertSetMoreStep} representing the insert operation for
   * the classification record.
   */
  protected InsertSetMoreStep<ClassificationRecord> insertClassificationStep(
    final Classification classification
  ) {
    return this.ctx.insertInto(CLASSIFICATION)
      .set(CLASSIFICATION.IDENTIFIER, classification.getIdentifier())
      .set(CLASSIFICATION.NAME, classification.getName());
  }

  @Override
  public void insertClassification(
    final Classification classification
  ) {
    this.insertClassificationStep(classification).execute();
  }

  @Override
  public Classification insertClassificationAndReturn(
    final Classification classification
  ) {
    final var rawClassification = this.insertClassificationStep(classification)
      .returning(CLASSIFICATION.fields())
      .fetchOneInto(CLASSIFICATION);

    return null != rawClassification
      ? ClassificationDao.toClassification(rawClassification)
      : null;
  }

  /**
   * Creates a step to update an existing {@link Classification} record in the
   * database.
   *
   * @param classification The {@link Classification} object containing the data
   *                       to be updated.
   * @return An {@link UpdateConditionStep} representing the update operation
   * for the classification record.
   */
  protected UpdateConditionStep<ClassificationRecord> updateClassificationStep(
    final Classification classification
  ) {
    return this.ctx.update(CLASSIFICATION)
      .set(CLASSIFICATION.NAME, classification.getName())
      .where(lower(CLASSIFICATION.IDENTIFIER).eq(classification.getIdentifier().toLowerCase()));
  }

  @Override
  public void updateClassification(
    final Classification classification
  ) {
    this.updateClassificationStep(classification).execute();
  }

  @Override
  public void updateClassifications(
    final Collection<Classification> classifications
  ) {
    if (null == classifications || classifications.isEmpty()) {
      return;
    }

    classifications.forEach(this::updateClassification);
  }

  @Override
  public Classification updateClassificationAndReturn(
    final Classification classification
  ) {
    final var rawClassification = this.updateClassificationStep(classification)
      .returning(CLASSIFICATION.fields())
      .fetchOneInto(CLASSIFICATION);

    return null != rawClassification
      ? ClassificationDao.toClassification(rawClassification)
      : null;
  }
}
