package me.kvdpxne.covilo.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.IBuilder;
import me.kvdpxne.covilo.domain.aggregation.Identity;

public record User(
  UUID identifier,
  String email,
  String password,
  Role role,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  City livingPlace,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) implements Identity<UUID>, Auditable {

  public String fullName() {
    return firstName + " " + lastName;
  }

  public boolean containsFullName() {
    return null != this.firstName && null != this.lastName
      && !this.firstName.isBlank() && !this.lastName.isBlank();
  }

  public static final class Builder implements IBuilder<User> {

    private UUID identifier;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private City livingPlace;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private Builder(
      final UUID identifier,
      final String email,
      final String password,
      final Role role,
      final String firstName,
      final String lastName,
      final Gender gender,
      final LocalDate birthDate,
      final City livingPlace,
      final LocalDateTime createdDate,
      final LocalDateTime lastModifiedDate
    ) {
      this.identifier = identifier;
      this.email = email;
      this.password = password;
      this.role = role;
      this.firstName = firstName;
      this.lastName = lastName;
      this.gender = gender;
      this.birthDate = birthDate;
      this.livingPlace = livingPlace;
      this.createdDate = createdDate;
      this.lastModifiedDate = lastModifiedDate;
    }

    public Builder() {
      super();
    }

    public Builder identifier(final UUID identifier) {
      this.identifier = identifier;
      return this;
    }

    public Builder email(final String email) {
      this.email = email;
      return this;
    }

    public Builder password(final String password) {
      this.password = password;
      return this;
    }

    public Builder role(final Role role) {
      this.role = role;
      return this;
    }

    public Builder firstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder gender(final Gender gender) {
      this.gender = gender;
      return this;
    }

    public Builder birthDate(final LocalDate birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    public Builder livingPlace(final City livingPlace) {
      this.livingPlace = livingPlace;
      return this;
    }

    public Builder createdDate(final LocalDateTime createdDate) {
      this.createdDate = createdDate;
      return this;
    }

    public Builder lastModifiedDate(final LocalDateTime lastModifiedDate) {
      this.lastModifiedDate = lastModifiedDate;
      return this;
    }

    @Override
    public User build() {
      if (null == this.identifier) {
        this.identifier = UUID.randomUUID();
      }

      if (null == this.createdDate) {
        this.createdDate = LocalDateTime.now();
      }

      return new User(
        this.identifier,
        this.email,
        this.password,
        this.role,
        this.firstName,
        this.lastName,
        this.gender,
        this.birthDate,
        this.livingPlace,
        this.createdDate,
        this.lastModifiedDate
      );
    }
  }

  public Builder toBuilder() {
    return new Builder(
      this.identifier,
      this.email,
      this.password,
      this.role,
      this.firstName,
      this.lastName,
      this.gender,
      this.birthDate,
      this.livingPlace,
      this.createdDate,
      this.lastModifiedDate
    );
  }
}
