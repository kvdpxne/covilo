package me.kvdpxne.covilo.presentation.payloads;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import me.kvdpxne.covilo.domain.exceptions.AgeRestrictionException;
import me.kvdpxne.covilo.domain.exceptions.PasswordMismatchException;
import me.kvdpxne.covilo.domain.service.UserService;

/**
 * Represents a signup request payload containing user registration
 * information.
 * <p>
 * This record is used to capture and validate the necessary data for user
 * registration.
 * </p>
 *
 * @param firstName       the first name of the user.
 * @param lastName        the last name of the user.
 * @param gender          the gender of the user, which can be 'Male', 'Female',
 *                        or 'None'.
 * @param birthDate       the birthdate of the user in the format YYYY-MM-DD.
 * @param email           the email address of the user.
 * @param password        the password for the user's account.
 * @param confirmPassword the confirmation of the password to ensure they
 *                        match.
 * @param privacyPolicy   a boolean indicating whether the user accepts the
 *                        privacy policy.
 * @since 0.1
 */
public record SignupRequest(
  @NotBlank(
    message = "First name cannot be blank."
  )
  @Size(
    message = "First name must be between 2 and 56 characters.",
    min = 2,
    max = 56
  )
  String firstName,

  @NotBlank(
    message = "Last name cannot be blank."
  )
  @Size(
    message = "Last name must be between 2 and 56 characters.",
    min = 2,
    max = 56
  )
  String lastName,

  @NotBlank(
    message = "Gender cannot be blank."
  )
  @Pattern(
    message = "Gender must be 'Male', 'Female', or 'None'.",
    regexp = "Male|Female|None",
    flags = {
      Pattern.Flag.CASE_INSENSITIVE,
    }
  )
  String gender,

  @NotBlank(
    message = "Birth date cannot be blank."
  )
  @Pattern(
    message = "Birth date must be in the format YYYY-MM-DD.",
    regexp = "\\d{4}-\\d{2}-\\d{2}"
  )
  String birthDate,

  @NotBlank(
    message = "Email cannot be blank."
  )
  @Email(
    message = "Email should be valid.",
    regexp = UserService.EMAIL_PATTERN,
    flags = {
      Pattern.Flag.CASE_INSENSITIVE,
    }
  )
  String email,

  @NotBlank(
    message = "Password cannot be blank."
  )
  @Size(
    message = "Password must be at least 8 characters long.",
    min = 8
  )
  String password,

  @NotBlank(
    message = "Confirm password cannot be blank."
  )
  @Size(
    message = "Confirm password must be at least 8 characters long.",
    min = 8
  )
  String confirmPassword,

  @AssertTrue(
    message = "You must accept the privacy policy."
  )
  boolean privacyPolicy
) {

  /**
   * Constructs a new SignupRequest and performs validation checks.
   * <p>
   * This constructor checks if the provided password matches the confirmation
   * password and verifies that the user's age is at least 16 years based on the
   * provided birth date.
   * </p>
   *
   * @throws PasswordMismatchException if the password and confirmPassword do
   *                                   not match.
   * @throws AgeRestrictionException   if the user is younger than 16 years.
   */
  public SignupRequest {
    // Check if the passwords match
    if (!password.equals(confirmPassword)) {
      throw new PasswordMismatchException(
        "Password and Confirm Password do not match."
      );
    }

    // Parse the birthdate
    final var birth = LocalDate.parse(birthDate);

    // Get the current date
    final var now = LocalDate.now();

    // Calculate the period between the birthdate and now
    final var period = Period.between(birth, now);

    // Check if the user is at least 16 years old
    if (16 > period.getYears()) {
      throw new AgeRestrictionException(
        "You must be at least 16 years old to sign up."
      );
    }
  }
}