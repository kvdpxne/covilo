package me.kvdpxne.covilo.domain.models;

import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.infrastructure.uid.Uid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains JUnit tests for the {@link Classification} model class.
 */
@Order(100)
public final class ClassificationTest {

  /**
   * A pre-built {@link Classification} instance used for testing purposes. This
   * classification has a randomly generated identifier and the name
   * "test_classification".
   */
  public static final Classification TEST_CLASSIFICATION =
    ClassificationTest.makeTestClassification();

  /**
   * Generates a test classification with a random identifier and name.
   *
   * @return A classification object with a random identifier and the name
   * "TEST_CLASSIFICATION".
   */
  public static Classification makeTestClassification() {
    try {
      Thread.sleep(3L);
    } catch (final InterruptedException exception) {
      throw new RuntimeException(exception);
    }

    return Classification.builder()
      .withRandomIdentifier()
      .withName("TEST_CLASSIFICATION")
      .build();
  }

  public static Classification makeRandomClassification() {
    return Classification.builder()
      .withRandomIdentifier()
      .withName(Uid.fast())
      .build();
  }

  //
  ClassificationTest() {
    // ...
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
    final var secondClassification = new Classification(
      classification.getIdentifier().toUpperCase(),
      classification.getName().toUpperCase()
    );

    // Verify that classifications are equal
    assertEquals(classification, secondClassification);
  }
}
