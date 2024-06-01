package me.kvdpxne.covilo.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.User;

/**
 *
 */
public record UserDto(
  String identifier,
  String email,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  LocalDateTime createdDate,
  LocalDateTime lastModifiedDate
) {

  /**
   *
   */
  public static UserDto from(
    final User user
  ) {
    return new UserDto(
      user.getIdentifier(),
      user.getLowerName(),
      user.getFirstName(),
      user.getLastName(),
      user.getGender(),
      user.getBirthDate(),
      user.getCreatedDate(),
      user.getLastModifiedDate()
    );
  }
}
