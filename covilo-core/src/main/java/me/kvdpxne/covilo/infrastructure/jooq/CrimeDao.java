package me.kvdpxne.covilo.infrastructure.jooq;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import org.jooq.DSLContext;
import org.jooq.generated.tables.records.CrimeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.*;
import static org.jooq.impl.DSL.select;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class CrimeDao
  implements CrimeRepository {

  private final ConfiguredPageFactory configuredPageFactory;

  private final DSLContext ctx;

  static Crime toCrime(
    final CrimeRecord crimeRecord
  ) {
    if (null == crimeRecord) {
      return null;
    }

    return new Crime(
      crimeRecord.getIdentifier(),
      crimeRecord.getTime(),
      null,
      crimeRecord.getReporterIdentifier(),
      crimeRecord.getIsConfirmed(),
      crimeRecord.getCreatedDate(),
      crimeRecord.getLastModifiedDate()
    );
  }

  @Override
  public long countCrimes() {
    return this.ctx.fetchCount(CRIME);
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
    return null;
//    return new BasePage<>(
//      this.dsl.select(CRIME.fields())
//        .select(CRIME_CATEGORIES.fields())
//        .select(CATEGORY.fields())
//        .select(CLASSIFICATION.fields())
//        .select(USER.fields())
//        .from(CRIME)
//        .join(CRIME_CATEGORIES)
//        .on(CRIME_CATEGORIES.CRIME_IDENTIFIER.eq(CRIME.IDENTIFIER))
//        .join(CATEGORY)
//        .on(CATEGORY.IDENTIFIER.eq(CRIME_CATEGORIES.CATEGORY_IDENTIFIER))
//        .join(CLASSIFICATION)
//        .on(CLASSIFICATION.IDENTIFIER.eq(CATEGORY.CLASSIFICATION_IDENTIFIER))
//        .limit(pageable.getSize())
//        .offset(pageable.getOffset())
//        .fetchGroups(
//          classificationRecord -> classificationRecord.into(CLASSIFICATION.fields())
//            .map(ClassificationDao::toClassification)
//        ),
//      pageable,
//      this.countCrimes()
//    );
  }

  @Override
  public Optional<Crime> findCrimeByIdentifier(
    final String identifier
  ) {
    return Optional.empty();
  }

  @Override
  public void insertCrimes(
    final Collection<Crime> crimes
  ) {

  }

  @Override
  public void insertCrime(
    final Crime crime
  ) {

  }

  @Override
  public Crime insertCrimeAndReturn(
    final Crime crime
  ) {
//    this.ctx.insertInto(CRIME_CATEGORIES)
//      .set(CRIME.IDENTIFIER, crime.getIdentifier())
//      .set()

    return CrimeDao.toCrime(
      this.ctx.insertInto(CRIME)
        .set(CRIME.IDENTIFIER, crime.getIdentifier())
        .set(CRIME.TIME, crime.getTime())
        .set(CRIME.REPORTER_IDENTIFIER, crime.getReporterIdentifier())
        .set(CRIME.IS_CONFIRMED, crime.isConfirmed())
        .set(CRIME.CREATED_DATE, crime.getCreatedDate())
        .set(CRIME.LAST_MODIFIED_DATE, (LocalDateTime) null)
        .returning(CRIME.fields())
        .fetchOneInto(CRIME)
    );
  }

  @Override
  public void updateCrime(
    final Crime crime
  ) {

  }

  @Override
  public boolean deleteCrimeByIdentifier(
    final String identifier
  ) {
    return 0 < this.ctx.deleteFrom(CRIME)
      .where(CRIME.IDENTIFIER.eq(identifier))
      .execute();
  }
}
