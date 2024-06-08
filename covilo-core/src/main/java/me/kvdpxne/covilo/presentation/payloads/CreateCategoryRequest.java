package me.kvdpxne.covilo.presentation.payloads;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import me.kvdpxne.covilo.domain.model.Category;

/**
 * Represents a request payload for creating a new category.
 * <p>
 * This record is used to capture and validate the necessary data for creating a
 * new category, including its name and classification.
 * </p>
 *
 * @param name                        the name of the category.
 * @param classification the request payload for creating a
 *                                    classification associated with this
 *                                    category.
 * @since 0.1
 */
public record CreateCategoryRequest(
  @Size(
    message = "Name must be between 2 and 30 characters.",
    min = 2,
    max = 30
  )
  String name,

  @Valid
  CreateClassificationRequest classification
) {

  public CreateCategoryRequest {
    if (null != name && name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank");
    }
  }

  /**
   * Converts this {@link CreateCategoryRequest} into a {@link Category}
   * object.
   * <p>
   * This method creates a new {@link Category} instance using the builder
   * pattern, setting its name and associated classification.
   * </p>
   *
   * @return a new {@link Category} object with the specified name and
   * classification.
   */
  public Category asCategory() {
    final var builder = Category.builder()
      .withName(this.name);

    final var classificationRequest = this.classification;
    if (null != classificationRequest) {
      builder.withClassification(classificationRequest.asClassification());
    }

    return builder.build();
  }
}
