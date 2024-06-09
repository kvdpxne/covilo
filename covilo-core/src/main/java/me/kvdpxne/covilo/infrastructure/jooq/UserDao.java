package me.kvdpxne.covilo.infrastructure.jooq;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import me.kvdpxne.covilo.infrastructure.jooq.utils.JooqOrderBy;
import org.jooq.BatchBindStep;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertSetMoreStep;
import org.jooq.UpdateConditionStep;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.USER;
import static org.jooq.impl.DSL.lower;

/**
 * DAO implementation for handling users using jOOQ.
 * This class interacts with the database using jOOQ DSLContext to perform
 * CRUD operations on users.
 *
 * @since 0.1
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class UserDao
  implements UserRepository {

  private static final Map<String, Field<?>> SORTS = Map.of(
    "createdDate", USER.CREATED_DATE,
    "lastModifiedDate", USER.LAST_MODIFIED_DATE,
    "birthDate", USER.BIRTH_DATE
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
   * Converts a {@link UserRecord} object to a {@link User} object.
   * <p>
   * If the input {@link UserRecord} is {@code null}, {@code null} is returned.
   * Otherwise, a new {@link User} object is created using the data from the
   * {@link UserRecord}.
   *
   * @param userRecord The {@link UserRecord} object to convert to a
   *                   {@link User} object.
   * @return A {@link User} object created from the provided
   *         {@link UserRecord}, or {@code null} if the input is {@code null}.
   */
  static User toUser(
    final UserRecord userRecord
  ) {
    // If the input UserRecord is null, return null
    if (null == userRecord) {
      return null;
    }

    // Otherwise, create a new User object using the data from the UserRecord
    return new User(
      userRecord.getIdentifier(),
      userRecord.getEmail(),
      userRecord.getPassword(),
      userRecord.getFirstName(),
      userRecord.getLastName(),
      Gender.of(userRecord.getIsMale()),
      userRecord.getBirthDate(),
      userRecord.getCreatedDate(),
      userRecord.getLastModifiedDate()
    );
  }

  @Override
  public long countUsers() {
    return this.ctx.fetchCount(USER);
  }

  @Override
  public void deleteUserByIdentifier(
    final String identifier
  ) {
    this.ctx.deleteFrom(USER)
      .where(USER.IDENTIFIER.eq(identifier))
      .execute();
  }

  @Override
  public void deleteUsersByIdentifiers(
    final Collection<String> identifiers
  ) {
    if (identifiers.isEmpty()) {
      return;
    }

    this.ctx.deleteFrom(USER)
      .where(USER.IDENTIFIER.in(identifiers))
      .execute();
  }

  /**
   * Deletes all users from the data source.
   * <p>
   * This method removes all user entries from the underlying data store
   * represented by the `USER` constant. Use this method with caution as it
   * permanently deletes all user data. Consider implementing backups or
   * confirmation prompts before using this method in production environments.
   * </p>
   */
  void deleteAllUsers() {
    this.ctx.deleteFrom(USER).execute();
  }

  @Override
  public boolean existsUserByIdentifier(
    final String identifier
  ) {
    return this.ctx.fetchExists(
      USER,
      lower(USER.IDENTIFIER).eq(identifier.toLowerCase())
    );
  }

  @Override
  public boolean existsUserByEmail(
    final String email
  ) {
    return this.ctx.fetchExists(
      USER,
      lower(USER.EMAIL).eq(email.toLowerCase())
    );
  }

  @Override
  public Page<User> findUsers(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> {
        final var orderBy = JooqOrderBy.orderBy(
          pageable.getSortable(),
          SORTS
        );

        return this.ctx.selectFrom(USER)
          .orderBy(orderBy)
          .limit(pageable.getSize())
          .offset(pageable.getOffset())
          .fetchInto(USER)
          .map(UserDao::toUser);
      },
      this::countUsers
    );
  }

  /**
   * Finds a user by a given condition.
   *
   * @param condition The condition to match.
   * @return An optional containing the matching user, if found.
   */
  private Optional<User> findUserBy(
    final Condition condition
  ) {
    return this.ctx.selectFrom(USER)
      .where(condition)
      .fetchOptionalInto(USER)
      .map(UserDao::toUser);
  }

  @Override
  public Optional<User> findUserByIdentifier(
    final String identifier
  ) {
    return this.findUserBy(
      lower(USER.IDENTIFIER).eq(identifier.toLowerCase())
    );
  }

  @Override
  public Optional<User> findUserByEmail(
    final String email
  ) {
    return this.findUserBy(
      lower(USER.EMAIL).eq(email.toLowerCase())
    );
  }

  /**
   * Constructs a database insertion step for inserting user data into the
   * {@code USER} table.
   *
   * @param user The {@link User} object containing the data to be inserted.
   * @return An {@link InsertSetMoreStep} object configured with the provided
   *         user data.
   */
  private InsertSetMoreStep<UserRecord> insertUserSetMoreStep(
    final User user
  ) {
    return this.ctx.insertInto(USER)
      .set(USER.IDENTIFIER, user.getIdentifier())
      .set(USER.EMAIL, user.getName())
      .set(USER.PASSWORD, user.getPassword())
      .set(USER.FIRST_NAME, user.getFirstName())
      .set(USER.LAST_NAME, user.getLastName())
      .set(USER.IS_MALE, user.getGenderValue())
      .set(USER.BIRTH_DATE, user.getBirthDate())
      .set(USER.CREATED_DATE, LocalDateTime.now(ZoneId.of("Z")))
      .setNull(USER.LAST_MODIFIED_DATE);
  }

  @Override
  public void insertUser(
    final User user
  ) {
    this.insertUserSetMoreStep(user).execute();
  }

  @Override
  public void insertUsers(
    final Collection<User> users
  ) {
    if (users.isEmpty()) {
      return;
    }

    final var insertStep = this.ctx.insertInto(
      USER,
      USER.IDENTIFIER,
      USER.EMAIL,
      USER.PASSWORD,
      USER.FIRST_NAME,
      USER.LAST_NAME,
      USER.IS_MALE,
      USER.BIRTH_DATE,
      USER.CREATED_DATE,
      USER.LAST_MODIFIED_DATE
    ).values(
      (String) null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );

    users.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(chunk -> {
        final var batch = this.ctx.batch(insertStep);

        chunk.forEach(step -> {
          // noinspection ResultOfMethodCallIgnored
          batch.bind(
            step.getIdentifier(),
            step.getName(),
            step.getPassword(),
            step.getFirstName(),
            step.getLastName(),
            step.getGenderValue(),
            step.getBirthDate(),
            step.getCreatedDate(),
            step.getLastModifiedDate()
          );
        });

        // Execute the batch insert operation
        batch.execute();
      });
  }

  @Override
  public User insertUserAndReturn(
    final User user
  ) {
    return UserDao.toUser(
      this.insertUserSetMoreStep(user)
        .returning(USER.fields())
        .fetchOneInto(USER)
    );
  }

  /**
   *
   */
  private UpdateConditionStep<UserRecord> updateUserStep(
    final User user
  ) {
    return this.ctx.update(USER)
      .set(USER.EMAIL, user.getName())
      .set(USER.PASSWORD, user.getPassword())
      .set(USER.FIRST_NAME, user.getFirstName())
      .set(USER.LAST_NAME, user.getLastName())
      .set(USER.IS_MALE, user.getGenderValue())
      .set(USER.BIRTH_DATE, user.getBirthDate())
      .set(USER.LAST_MODIFIED_DATE, LocalDateTime.now(ZoneId.of("Z")))
      .where(USER.IDENTIFIER.eq(user.getIdentifier()));
  }

  @Override
  public void updateUser(
    final User user
  ) {
    this.updateUserStep(user).execute();
  }

  @Override
  public void updateUsers(
    final Collection<User> users
  ) {
    users.forEach(this::updateUser);
  }

  @Override
  public User updateUserAndReturn(
    final User user
  ) {
    return UserDao.toUser(
      this.updateUserStep(user)
        .returning(USER.fields())
        .fetchOneInto(USER)
    );
  }
}
