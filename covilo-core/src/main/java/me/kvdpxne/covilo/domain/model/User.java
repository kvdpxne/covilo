package me.kvdpxne.covilo.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import me.kvdpxne.covilo.domain.aggregation.Auditable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;

/**
 * Represents a user entity.
 *
 * @param identifier       The unique identifier for the user.
 * @param email            The email address of the user.
 * @param password         The password of the user.
 * @param role             The role of the user.
 * @param firstName        The first name of the user.
 * @param lastName         The last name of the user.
 * @param gender           The gender of the user.
 * @param birthDate        The birthdate of the user.
 * @param livingPlace      The city where the user lives.
 * @param createdDate      The date and time when the user was created.
 * @param lastModifiedDate The date and time when the user was last modified.
 */
@Builder(toBuilder = true)
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
) implements Identifiable<UUID>, Auditable {

  /**
   * Constructs and returns the full name by concatenating the first name and
   * last name.
   *
   * @return The full name of the user.
   */
  public String getFullName() {
    return STR."\{this.firstName} \{this.lastName}";
  }

  /**
   * Generates and returns the file name for the user's avatar.
   * The file name is constructed based on the user's identifier.
   *
   * @return The file name for the user's avatar.
   */
  public String getAvatarFileName() {
    return STR."\{this.identifier.toString()}.webp";
  }
}
