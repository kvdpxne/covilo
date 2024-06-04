package me.kvdpxne.covilo.presentation.payloads;

import jakarta.validation.constraints.Size;
import me.kvdpxne.covilo.domain.model.Classification;

/**
 * Represents a request payload for creating a new classification.
 * <p>
 * This record is used to capture and validate the necessary data for creating a
 * new classification.
 * </p>
 *
 * @param name the name of the classification, which must be between 2 and 30
 *             characters.
 * @since 0.1
 */
public record CreateClassificationRequest(
  @Size(
    message = "Name must be between 2 and 30 characters.",
    min = 2,
    max = 30
  )
  String name
) {

  public CreateClassificationRequest {
    if (null != name && name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank.");
    }
  }

  /**
   * Converts this {@link CreateClassificationRequest} into a
   * {@link Classification} object.
   * <p>
   * This method creates a new {@link Classification} instance using the builder
   * pattern.
   * </p>
   *
   * @return a new {@link Classification} object with the name from this
   * request.
   */
  public Classification asClassification() {
    return Classification.builder()
      .withName(this.name)
      .build();
  }
}
