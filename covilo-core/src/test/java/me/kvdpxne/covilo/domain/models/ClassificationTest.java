package me.kvdpxne.covilo.domain.models;

import me.kvdpxne.covilo.domain.model.Classification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains JUnit tests for the {@link Classification} model class.
 */
public final class ClassificationTest {

  /**
   * A pre-built {@link Classification} instance used for testing purposes. This
   * classification has a randomly generated identifier and the name
   * "test_classification".
   */
  public static final Classification TEST_CLASSIFICATION =
    ClassificationTest.makeTestClassification();

  //
  ClassificationTest() {
    // ...
  }

  /**
   * Generates a test classification with a random identifier and name.
   *
   * @return A classification object with a random identifier and the name
   * "test_classification".
   */
  public static Classification makeTestClassification() {
    try {
      Thread.sleep(2);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }

    return Classification.builder()
      .withRandomIdentifier()
      .withName("test_classification")
      .build();
  }

  /**
   * Test case to verify the equals method of {@link Classification} considers
   * both identifier and name (case-insensitively if applicable).
   */
  @DisplayName("Classification Equals (Case-Insensitive)")
  @Test
  void equalsClassification() {
    // Get the test classification data
    final var classification = TEST_CLASSIFICATION;

    // Create a second classification with lowercase identifier and name
    final var secondClassification = classification.toBuilder()
      .withIdentifier(classification.getIdentifier().toLowerCase())
      .withName(classification.getName().toLowerCase())
      .build();

    // Verify that classifications are equal
    assertEquals(classification, secondClassification);
  }
}
