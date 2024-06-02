package me.kvdpxne.covilo.presentation.advices;

import java.time.Instant;
import java.util.stream.Stream;
import me.kvdpxne.covilo.infrastructure.uid.Uid;

/**
 * Represents a comprehensive error response.
 *
 * @param status    The HTTP status code of the error response.
 * @param code      A custom error code representing the type of error.
 * @param message   A human-readable message describing the error.
 * @param details   An array of detailed error information.
 * @param timestamp The timestamp when the error occurred.
 * @param trace     A unique trace identifier for tracking the error.
 * @param instance  The instance URI associated with the request that caused the
 *                  error.
 * @since 0.1
 */
public record ErrorResponse(
  int status,
  String code,
  String message,
  ErrorDetails[] details,
  String timestamp,
  String trace,
  String instance
) {

  /**
   * Creates an {@link ErrorResponse} object from the provided details.
   * <p>
   * This method generates an {@link ErrorResponse} with the current timestamp
   * and a unique trace identifier.
   * </p>
   *
   * @param status   The HTTP status code of the error response.
   * @param code     A custom error code representing the type of error.
   * @param message  A human-readable message describing the error.
   * @param details  A stream of {@link ErrorDetails} providing detailed error
   *                 information.
   * @param instance The instance URI associated with the request that caused
   *                 the error.
   * @return A new {@link ErrorResponse} object encapsulating the error details.
   */
  public static ErrorResponse from(
    final int status,
    final String code,
    final String message,
    final Stream<ErrorDetails> details,
    final String instance
  ) {
    // Generate the current timestamp
    final var timestamp = Instant.now().toString();

    // Generate a unique trace identifier
    final var traceIdentifier = Uid.next();

    // Create and return the ErrorResponse
    return new ErrorResponse(
      status,
      code,
      message,
      details.toArray(ErrorDetails[]::new),
      timestamp,
      traceIdentifier,
      instance
    );
  }
}
