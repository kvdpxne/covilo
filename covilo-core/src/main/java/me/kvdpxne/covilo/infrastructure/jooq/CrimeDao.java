package me.kvdpxne.covilo.infrastructure.jooq;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import me.kvdpxne.covilo.infrastructure.jooq.utils.JooqOrderBy;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertSetMoreStep;
import org.jooq.UpdateConditionStep;
import org.jooq.generated.tables.records.CrimeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.generated.Tables.CATEGORY;
import static org.jooq.generated.Tables.CRIME;
import static org.jooq.generated.Tables.CRIME_CATEGORIES;
import static org.jooq.generated.Tables.USER;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class CrimeDao
  implements CrimeRepository {

  /**
   *
   */
  private static final Map<String, Field<?>> SORTS = Map.of(
    "createdDate", CRIME.CREATED_DATE,
    "lastModifiedDate", CRIME.LAST_MODIFIED_DATE
  );

  /**
   * The factory for creating configured pages.
   */
  private final ConfiguredPageFactory configuredPageFactory;

  /**
   * The DSL context for jOOQ queries.
   */
  private final DSLContext ctx;

  /**
   * Converts a {@link CrimeRecord} to a {@link Crime} object.
   *
   * @param crime      The {@link CrimeRecord} to convert.
   * @param categories The categories associated with the crime.
   * @param user       The user who reported the crime.
   * @return The corresponding {@link Crime} object.
   */
  static Crime toCrime(
    final CrimeRecord crime,
    final Collection<Category> categories,
    final User user
  ) {
    if (null == crime) {
      return null;
    }

    return Crime.builder()
      .withIdentifier(crime.getIdentifier())
      .withTime(crime.getTime())
      .withCoordinates(crime.getLatitude(), crime.getLongitude())
      .withCategories(categories)
      .withReporter(user)
      .withConfirmed(crime.getIsConfirmed())
      .withCreatedDate(crime.getCreatedDate())
      .withLastModifiedDate(crime.getLastModifiedDate())
      .build();
  }

  /**
   * Retrieves the categories associated with a given crime identifier.
   *
   * @param identifier The identifier of the crime.
   * @return A collection of categories associated with the crime.
   */
  private Collection<Category> findCrimeCategories(
    final String identifier
  ) {
    return this.ctx.selectFrom(CRIME_CATEGORIES)
      .where(CRIME_CATEGORIES.CRIME_IDENTIFIER.eq(identifier))
      .fetchInto(CRIME_CATEGORIES)
      .map(it -> CategoryDao
        .findCategoryByIdentifierOrNull(this.ctx, it.getCategoryIdentifier())
      );
//    return this.ctx.select(CATEGORY.fields())
//      .from(CATEGORY)
//      .leftJoin(CRIME_CATEGORIES)
//      .on(CRIME_CATEGORIES.CRIME_IDENTIFIER.eq(identifier))
//      .fetchInto(CATEGORY)
//      .map(category -> CategoryDao.toCategory(this.ctx, category));
  }

  /**
   * Retrieves the user who reported a given crime.
   *
   * @param identifier The identifier of the crime reporter.
   * @return The user who reported the crime.
   */
  private User findCrimeReporter(
    final String identifier
  ) {
    return UserDao.toUser(
      this.ctx.selectFrom(USER)
        .where(USER.IDENTIFIER.eq(identifier))
        .fetchOneInto(USER)
    );
  }

  /**
   * Converts a {@link CrimeRecord} to a {@link Crime} object including its
   * categories and reporter.
   *
   * @param crime The {@link CrimeRecord} to convert.
   * @return The corresponding {@link Crime} object.
   */
  protected Crime toCrime(
    final CrimeRecord crime
  ) {
    final var categories = this.findCrimeCategories(crime.getIdentifier());
    final var user = this.findCrimeReporter(crime.getReporterIdentifier());

    return CrimeDao.toCrime(
      crime,
      categories,
      user
    );
  }

  @Override
  public long countCrimes() {
    return this.ctx.fetchCount(CRIME);
  }

  @Override
  public long countCrimeCategories() {
    return this.ctx.fetchCount(CRIME_CATEGORIES);
  }

  @Transactional
  @Override
  public void deleteCrimeCategoriesByIdentifier(
    final String identifier
  ) {
    this.ctx.deleteFrom(CRIME_CATEGORIES)
      .where(CRIME_CATEGORIES.CRIME_IDENTIFIER.eq(identifier))
      .execute();
  }

  @Transactional
  @Override
  public void deleteCrimeByIdentifier(
    final String identifier
  ) {
    this.ctx.deleteFrom(CRIME)
      .where(CRIME.IDENTIFIER.eq(identifier))
      .execute();

    this.deleteCrimeCategoriesByIdentifier(identifier);
  }

  @Override
  public boolean existsCrimeByIdentifier(
    final String identifier
  ) {
    return this.ctx.fetchExists(
      CRIME,
      CRIME.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Page<Crime> getCrimes(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> this.ctx.selectFrom(CRIME)
        .limit(pageable.getSize())
        .offset(pageable.getOffset())
        .fetchInto(CRIME)
        .map(this::toCrime),
      this::countCrimes
    );
  }

  @Override
  public Page<Crime> getLatestCrimes(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> {
        //
        final var orderBy = JooqOrderBy.orderBy(
          pageable.getSortable(),
          SORTS
        );

        return this.ctx.selectFrom(CRIME)
          .orderBy(orderBy)
          .limit(pageable.getSize())
          .offset(pageable.getOffset())
          .fetchInto(CRIME)
          .map(this::toCrime);
      },
      this::countCrimes
    );
  }

  @Override
  public Optional<Crime> findCrimeByIdentifier(
    final String identifier
  ) {
    return this.ctx.selectFrom(CRIME)
      .where(CRIME.IDENTIFIER.eq(identifier))
      .fetchOptionalInto(CRIME)
      .map(this::toCrime);
  }

  /**
   * Inserts the categories associated with a {@link Crime}.
   *
   * @param identifier the identifier of the {@link Crime}.
   * @param categories the list of {@link Category} objects to be inserted.
   */
  @Transactional
  protected void insertCrimeCategories(
    final String identifier,
    final Collection<Category> categories
  ) {
    if (categories.isEmpty()) {
      return;
    }

    // Create a template insert step for batch processing
    final var insertStep = this.ctx.insertInto(
      CRIME_CATEGORIES,
      CRIME_CATEGORIES.CRIME_IDENTIFIER,
      CRIME_CATEGORIES.CATEGORY_IDENTIFIER
    ).values(
      (String) null,
      null
    );

    // Split the categories into chunks and batch insert them
    categories.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(chunk -> {
        final var batch = this.ctx.batch(insertStep);

        // Bind the identifier and category identifier for each chunk
        chunk.forEach(step -> {
          // noinspection ResultOfMethodCallIgnored
          batch.bind(identifier, step.getIdentifier());
        });

        // Execute the batch insert
        batch.execute();
      });
  }

  @Transactional
  @Override
  public void insertCrimes(
    final Collection<Crime> crimes
  ) {
    if (crimes.isEmpty()) {
      return;
    }

    // Create a template insert step for batch processing
    final var insertStep = this.ctx.insertInto(
      CRIME,
      CRIME.IDENTIFIER,
      CRIME.TIME,
      CRIME.LATITUDE,
      CRIME.LONGITUDE,
      CRIME.REPORTER_IDENTIFIER,
      CRIME.IS_CONFIRMED,
      CRIME.CREATED_DATE,
      CRIME.LAST_MODIFIED_DATE
    ).values(
      (String) null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );

    // Split the crimes into chunks and batch insert them
    crimes.parallelStream()
      .gather(Gatherers.windowFixed(8))
      .forEach(chunk -> {
        final var batch = this.ctx.batch(insertStep);

        //
        chunk.forEach(step -> {
          // noinspection ResultOfMethodCallIgnored
          batch.bind(
            step.getIdentifier(),
            step.getTime(),
            step.getCoordinatesLatitude(),
            step.getCoordinatesLongitude(),
            step.getReporter().getIdentifier(),
            step.isConfirmed(),
            step.getCreatedDate(),
            step.getLastModifiedDate()
          );

          //
          this.insertCrimeCategories(
            step.getIdentifier(),
            step.getCategories()
          );
        });

        // Execute the batch insert
        batch.execute();
      });
  }

  /**
   * Prepares the insertion of a {@link Crime} object into the CRIME table.
   *
   * @param crime The {@link Crime} object to be inserted.
   * @return The InsertSetMoreStep object containing the prepared steps.
   */
  @Transactional
  protected InsertSetMoreStep<CrimeRecord> insertCrimeStep(
    final Crime crime
  ) {
    // Construct the initial insert statement with mandatory fields
    final var insertStep = this.ctx.insertInto(CRIME)
      .set(CRIME.IDENTIFIER, crime.getIdentifier())
      .set(CRIME.TIME, crime.getTime())
      .set(CRIME.LATITUDE, crime.getCoordinatesLatitude())
      .set(CRIME.LONGITUDE, crime.getCoordinatesLongitude())
      .set(CRIME.REPORTER_IDENTIFIER, crime.getReporter().getIdentifier())
      .set(CRIME.IS_CONFIRMED, crime.isConfirmed())
      .set(CRIME.CREATED_DATE, crime.getCreatedDate())
      .setNull(CRIME.LAST_MODIFIED_DATE);

    // Insert associated categories
    this.insertCrimeCategories(
      crime.getIdentifier(),
      crime.getCategories()
    );

    return insertStep;
  }

  @Transactional
  @Override
  public void insertCrime(
    final Crime crime
  ) {
    this.insertCrimeStep(crime).execute();
  }

  @Transactional
  @Override
  public Crime insertCrimeAndReturn(
    final Crime crime
  ) {
    final var rawCrime = this.insertCrimeStep(crime)
      .returning(CRIME.fields())
      .fetchOneInto(CRIME);

    return null != rawCrime
      ? this.toCrime(rawCrime)
      : null;
  }

  /**
   * Updates a {@link CrimeRecord} in the database.
   * <p>
   * This method updates the crime record in the database with the provided
   * crime details, deletes the existing categories associated with the crime,
   * and inserts the new categories.
   * </p>
   *
   * @param crime the {@link Crime} object containing the updated details.
   * @return the {@link UpdateConditionStep} for the update query.
   */
  @Transactional
  protected UpdateConditionStep<CrimeRecord> updateCrimeStep(
    final Crime crime
  ) {
    final var updateStep = this.ctx.update(CRIME)
      .set(CRIME.TIME, crime.getTime())
      .set(CRIME.LATITUDE, crime.getCoordinatesLatitude())
      .set(CRIME.LONGITUDE, crime.getCoordinatesLongitude())
      .set(CRIME.REPORTER_IDENTIFIER, crime.getReporter().getIdentifier())
      .set(CRIME.IS_CONFIRMED, crime.isConfirmed())
      .set(CRIME.LAST_MODIFIED_DATE, LocalDateTime.now(ZoneId.of("Z")))
      .where(CRIME.IDENTIFIER.eq(crime.getIdentifier()));

    // Delete the existing crime categories
    this.deleteCrimeCategoriesByIdentifier(crime.getIdentifier());

    // Insert the new crime categories
    this.insertCrimeCategories(
      crime.getIdentifier(),
      crime.getCategories()
    );

    return updateStep;
  }

  @Transactional
  @Override
  public void updateCrimes(
    final Collection<Crime> crimes
  ) {
    crimes.forEach(it -> this.updateCrimeStep(it).execute());
  }

  @Transactional
  @Override
  public void updateCrime(
    final Crime crime
  ) {
    this.updateCrimeStep(crime).execute();
  }

  @Transactional
  @Override
  public Crime updateCrimeAndReturn(
    final Crime crime
  ) {
    final var rawCrime = this.updateCrimeStep(crime)
      .returning(CRIME.fields())
      .fetchOneInto(CRIME);

    return null != rawCrime
      ? this.toCrime(rawCrime)
      : null;
  }
}
