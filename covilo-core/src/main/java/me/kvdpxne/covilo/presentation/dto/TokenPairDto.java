package me.kvdpxne.covilo.presentation.dto;

import me.kvdpxne.covilo.domain.model.TokenPair;
import me.kvdpxne.covilo.shared.Validation;

/**
 * A Data Transfer Object (DTO) for transferring token pair information
 * between different layers of the application.
 *
 * @param accessToken  The access token string.
 * @param refreshToken The refresh token string.
 * @param expiry       The expiry time in milliseconds since the epoch.
 * @param tokenType    The type of the token (e.g., BEARER).
 */
public record TokenPairDto(
  String accessToken,
  String refreshToken,
  long expiry,
  String tokenType
) {

  /**
   * Creates a {@link TokenPairDto} from a {@link TokenPair} object.
   * This method checks if the provided {@link TokenPair} is valid and
   * then converts it to a {@link TokenPairDto}.
   *
   * @param tokenPair The {@link TokenPair} object to convert.
   * @return A new {@link TokenPairDto} containing the same information
   *         as the provided {@link TokenPair}.
   * @throws NullPointerException if the provided {@link TokenPair} is null.
   */
  public static TokenPairDto fromTokenPair(
    final TokenPair tokenPair
  ) {
    Validation.check(
      tokenPair,
      () -> "TokenPair must not be null."
    );
    return new TokenPairDto(
      tokenPair.getAccessToken(),
      tokenPair.getRefreshToken(),
      tokenPair.getExpiry().toEpochMilli(),
      tokenPair.getTokenType().name()
    );
  }
}
