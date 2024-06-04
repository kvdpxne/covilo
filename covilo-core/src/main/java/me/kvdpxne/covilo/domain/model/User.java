package me.kvdpxne.covilo.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.domain.aggregation.Nameable;
import me.kvdpxne.covilo.infrastructure.uid.Uid;

/**
 * Represents a user entity.
 */
@SuppressWarnings("LombokGetterMayBeUsed")
public final class User
  implements Identifiable<String>, Auditable, Nameable {

  private final String identifier;
  private final String email;
  private final String encryptedPassword;
  private final String firstName;
  private final String lastName;
  private final Gender gender;
  private final LocalDate birthDate;
  private final LocalDateTime createdDate;
  private final LocalDateTime lastModifiedDate;

  /**
   * Constructs a new user instance with the specified properties.
   *
   * @param identifier        The unique identifier of the user.
   * @param email             The email address of the user.
   * @param encryptedPassword The encrypted password of the user.
   * @param firstName         The first name of the user.
   * @param lastName          The last name of the user.
   * @param gender            The gender of the user.
   * @param birthDate         The birthdate of the user.
   * @param createdDate       The date and datetime when the user was created.
   * @param lastModifiedDate The date and datetime when the user was last modified.
   */
  public User(
    final String identifier,
    final String email,
    final String encryptedPassword,
    final String firstName,
    final String lastName,
    final Gender gender,
    final LocalDate birthDate,
    final LocalDateTime createdDate,
    final LocalDateTime lastModifiedDate
  ) {
    this.identifier = identifier;
    this.email = email;
    this.encryptedPassword = encryptedPassword;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.birthDate = birthDate;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  /**
   * Returns a new builder instance to build a {@link User} object.
   *
   * @return A {@link UserBuilder} instance.
   */
  public static UserBuilder builder() {
    return new UserBuilder();
  }

  /**
   * Returns a new builder initialized with the current {@link User}'s state.
   *
   * @return A {@link UserBuilder} instance.
   */
  public UserBuilder toBuilder() {
    return new UserBuilder(
      this.identifier,
      this.email,
      this.encryptedPassword,
      this.firstName,
      this.lastName,
      this.gender,
      this.birthDate,
      this.createdDate,
      this.lastModifiedDate
    );
  }
  /**
   * Retrieves the unique identifier of the user.
   *
   * @return The identifier.
   */

  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  @Override
  public String getName() {
    return this.email;
  }

  public String getPassword() {
    return this.encryptedPassword;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public Gender getGender() {
    return this.gender;
  }

  public Boolean getGenderValue() {
    return switch (this.gender) {
      case MALE -> true;
      case FEMALE -> false;
      case NONE -> null;
      // noinspection UnnecessaryDefault
      default -> throw new IllegalStateException(
        STR."Unexpected value: \{this.gender}"
      );
    };
  }

  public LocalDate getBirthDate() {
    return this.birthDate;
  }

  @Override
  public LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  @Override
  public LocalDateTime getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  /**
   * A builder class for constructing {@link User} objects.
   */
  public static final class UserBuilder
    implements Buildable<User> {

    private String identifier;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate brithDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    /**
     * Constructs a new user builder with the specified identifier, email,
     * encrypted password, first name, last name, gender, birth date, created
     * date, and last modified date.
     *
     * @param identifier        The unique identifier of the user.
     * @param email             The email address of the user.
     * @param password The encrypted password of the user.
     * @param firstName         The first name of the user.
     * @param lastName          The last name of the user.
     * @param gender            The gender of the user.
     * @param birthDate         The birthdate of the user.
     * @param createdDate       The date and datetime when the user was created.
     * @param lastModifiedDate  The date and datetime when the user was last
     *                          modified.
     */
    private UserBuilder(
      final String identifier,
      final String email,
      final String password,
      final String firstName,
      final String lastName,
      final Gender gender,
      final LocalDate birthDate,
      final LocalDateTime createdDate,
      final LocalDateTime lastModifiedDate
    ) {
      this.identifier = identifier;
      this.email = email;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.gender = gender;
      this.brithDate = birthDate;
      this.createdDate = createdDate;
      this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Default constructor.
     */
    private UserBuilder() {
      // ...
    }

    public UserBuilder withIdentifier(
      final String identifier
    ) {
      this.identifier = identifier;
      return this;
    }

    public UserBuilder withRandomIdentifier() {
      this.identifier = Uid.next();
      return this;
    }

    public UserBuilder withEmail(
      final String email
    ) {
      this.email = email;
      return this;
    }

    public UserBuilder withPassword(
      final String encryptedPassword
    ) {
      this.password = encryptedPassword;
      return this;
    }

    public UserBuilder withFirstName(
      final String firstName
    ) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder withLastName(
      final String lastName
    ) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder withGender(
      final Gender gender
    ) {
      this.gender = gender;
      return this;
    }

    public UserBuilder withGender(
      final String gender
    ) {
      this.gender = Gender.valueOf(gender.toUpperCase());
      return this;
    }

    public UserBuilder withGenderValue(
      final Boolean genderValue
    ) {
      this.gender = Gender.of(genderValue);
      return this;
    }

    public UserBuilder withBrithDate(
      final LocalDate brithDate
    ) {
      this.brithDate = brithDate;
      return this;
    }

    public UserBuilder withBrithDate(
      final String birthDate
    ) {
      return this.withBrithDate(LocalDate.parse(birthDate));
    }

    public UserBuilder withCreatedDate(
      final LocalDateTime createdDate
    ) {
      this.createdDate = createdDate;
      return this;
    }

    public UserBuilder withCreatedDate() {
      return this.withCreatedDate(LocalDateTime.now());
    }

    public UserBuilder withLastModifiedDate(
      final LocalDateTime lastModifiedDate
    ) {
      this.lastModifiedDate = lastModifiedDate;
      return this;
    }

    public UserBuilder withLastModifiedDate() {
      return this.withLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public User build() {
      return new User(
        this.identifier,
        this.email,
        this.password,
        this.firstName,
        this.lastName,
        this.gender,
        this.brithDate,
        this.createdDate,
        this.lastModifiedDate
      );
    }
  }
}
