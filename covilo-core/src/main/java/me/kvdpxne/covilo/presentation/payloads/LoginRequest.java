package me.kvdpxne.covilo.presentation.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import me.kvdpxne.covilo.domain.service.UserService;

/**
 * Represents the payload for a login request.
 * <p>
 * This record captures the user's email and password for authentication
 * purposes. Validation constraints ensure that the provided email and password
 * meet the required format and length.
 * </p>
 *
 * @param email    The email address of the user attempting to log in. It cannot
 *                 be blank and must match a valid email pattern.
 * @param password The password of the user attempting to log in. It cannot be
 *                 blank and must be at least eight characters long.
 */
public record LoginRequest(
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
  String password
) {}
