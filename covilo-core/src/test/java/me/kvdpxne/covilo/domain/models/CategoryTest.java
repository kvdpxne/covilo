package me.kvdpxne.covilo.domain.models;

import me.kvdpxne.covilo.domain.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * This class contains JUnit tests for the {@link Category} model class.
 */
public final class CategoryTest {

  /**
   * A pre-built {@link Category} instance used for testing purposes. This
   * category has a randomly generated identifier, the name "test_category", and
   * a reference to the {@link ClassificationTest#TEST_CLASSIFICATION}.
   */
  public static final Category TEST_CATEGORY;

  static {
    TEST_CATEGORY = Category.builder()
      .withRandomIdentifier()
      .withName("test_category")
      .withClassification(ClassificationTest.TEST_CLASSIFICATION)
      .build();
  }

  //
  CategoryTest() {
    // ...
  }

  /**
   * Test case to verify the equals method of {@link Category} considers both
   * identifier and name (case-insensitively if applicable).
   */
  @Order(1)
  @DisplayName("Category Equals (Case-Insensitive)")
  @Test
  void equalsCategory() {
    // Get the test category data
    final var category = TEST_CATEGORY;

    // Create a second category with lowercase identifier and name
    final var secondCategory = category.toBuilder()
      .withIdentifier(category.getIdentifier().toLowerCase())
      .withName(category.getName().toLowerCase())
      .build();

    // Verify that categories are equal
    Assertions.assertEquals(category, secondCategory);
  }
}
