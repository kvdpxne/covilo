package me.kvdpxne.covilo.infrastructure.jooq;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Gatherers;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.model.pagination.Page;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.domain.service.ConfiguredPageFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.jooq.generated.Tables.USER;

/**
 * DAO implementation for handling users using jOOQ.
 * This class interacts with the database using jOOQ DSLContext to perform
 * CRUD operations on users.
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class UserDao
  implements UserRepository {

  /**
   * The factory for creating configured pages.
   */
  private final ConfiguredPageFactory configuredPageFactory;

  /**
   * The DSL context for jOOQ queries.
   */
  private final DSLContext ctx;

  @Override
  public long countUsers() {
    return this.ctx.fetchCount(USER);
  }

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
  public boolean existsUserByIdentifier(
    final UUID identifier
  ) {
    return this.ctx.fetchExists(
      USER,
      USER.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public boolean existsUserByEmail(
    final String email
  ) {
    return this.ctx.fetchExists(
      USER,
      USER.EMAIL.eq(email)
    );
  }

  @Override
  public Page<User> findUsers(
    final Pageable pageable
  ) {
    return this.configuredPageFactory.createPage(
      pageable,
      () -> this.ctx.select(USER.fields())
        .from(USER)
        .limit(pageable.getSize())
        .offset(pageable.getOffset())
        .fetchInto(USER)
        .map(UserDao::toUser),
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
    final UUID identifier
  ) {
    return this.findUserBy(
      USER.IDENTIFIER.eq(identifier)
    );
  }

  @Override
  public Optional<User> findUserByEmail(
    final String email
  ) {
    return this.findUserBy(
      USER.EMAIL.eq(email)
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
      .set(USER.CREATED_DATE, user.getCreatedDate())
      .set(USER.LAST_MODIFIED_DATE, (LocalDateTime) null);
  }

  @Override
  public void insertUsers(
    final Collection<User> users
  ) {
    // noinspection preview
    users.stream()
      .gather(Gatherers.windowFixed(8))
      .forEach(listableUsers -> this.ctx.insertInto(
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
      ).values(listableUsers).execute());
  }

  @Override
  public void insertUser(
    final User user
  ) {
    this.insertUserSetMoreStep(user).execute();
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

  @Override
  public void updateUser(
    final User user
  ) {
    this.ctx.update(USER)
      .set(USER.EMAIL, user.getName())
      .set(USER.PASSWORD, user.getPassword())
      .set(USER.FIRST_NAME, user.getFirstName())
      .set(USER.LAST_NAME, user.getLastName())
      .set(USER.IS_MALE, user.getGenderValue())
      .set(USER.BIRTH_DATE, user.getBirthDate())
      .set(USER.LAST_MODIFIED_DATE, user.getLastModifiedDate())
      .where(USER.IDENTIFIER.eq(user.getIdentifier()))
      .execute();
  }

  @Override
  public boolean deleteUserByIdentifier(
    final UUID identifier
  ) {
    return 0 < this.ctx.deleteFrom(USER)
      .where(USER.IDENTIFIER.eq(identifier))
      .execute();
  }
}
