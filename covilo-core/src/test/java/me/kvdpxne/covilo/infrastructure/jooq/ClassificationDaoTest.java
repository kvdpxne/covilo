package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.ArrayList;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.models.ClassificationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for {@link ClassificationDao}.
 */
@SpringBootTest
final class ClassificationDaoTest {

  /**
   * Instance of the {@link ClassificationDao} to be tested.
   */
  private final ClassificationDao classificationDao;

  /**
   * Autowires the {@link ClassificationDao} bean.
   *
   * @param classificationDao The {@link ClassificationDao} bean to be injected.
   */
  @Autowired
  ClassificationDaoTest(
    final ClassificationDao classificationDao
  ) {
    this.classificationDao = classificationDao;
  }

  /**
   * Deletes all classifications after each test and verifies their deletion.
   */
  @AfterEach
  void tearDown() {
    this.classificationDao.deleteAllClassifications();
    assertEquals(0, this.classificationDao.countClassifications());
  }

  /**
   * Test case (Order=1) to verify insertion of a single {@link Classification}
   * record.
   */
  @Order(1)
  @DisplayName("Insert Single Classification")
  @Test
  void insertClassification() {
    // Get the test classification data
    final var classification = ClassificationTest.TEST_CLASSIFICATION;

    // Insert the classification
    this.classificationDao.insertClassification(classification);

    // Verify that one classification record is present
    assertEquals(1, this.classificationDao.countClassifications());
  }

  /**
   * Test case (Order=2) to verify insertion of multiple {@link Classification}
   * records.
   */
  @Order(2)
  @DisplayName("Insert Multiple Classifications")
  @Test
  void insertMultipleClassifications() {
    // Number of classifications to insert
    final var size = 500;

    // Create a list to hold the classifications
    final var classifications = new ArrayList<Classification>();

    // Generate classifications with random identifiers and dynamic names
    for (var i = 0; i < size; ++i) {
      classifications.add(
        Classification.builder()
          .withRandomIdentifier()
          .withName(STR."test_classification_\{i}")
          .build()
      );
    }

    // Insert the classifications
    this.classificationDao.insertClassifications(classifications);

    // Verify that the expected number of classifications was inserted
    assertEquals(size, this.classificationDao.countClassifications());
  }

  /**
   * Test case (Order=3) to verify insertion and retrieval of a
   * {@link Classification} record.
   */
  @Order(3)
  @DisplayName("Insert and Retrieve Classification")
  @Test
  void insertClassificationAndReturn() {
    // Get the test classification data
    final var classification = ClassificationTest.TEST_CLASSIFICATION;
    final var identifier = classification.getIdentifier();
    final var name = classification.getName();

    // Insert and retrieve the classification
    final var newClassification = this.classificationDao
      .insertClassificationAndReturn(classification);

    // Verify retrieved classification data
    assertEquals(1, this.classificationDao.countClassifications());
    assertNotNull(newClassification);
    assertEquals(identifier, newClassification.getIdentifier());
    assertEquals(name, newClassification.getName());
    assertEquals(classification, newClassification);
  }

  /**
   * Test case (Order=4) to verify finding a {@link Classification} by
   * identifier (case-insensitive).
   */
  @Order(4)
  @DisplayName("Find Classification By Identifier (Case-Insensitive)")
  @Test
  void findClassificationByIdentifier() {
    // Insert a test classification
    this.insertClassification();

    // Get the test classification data
    final var classification = ClassificationTest.TEST_CLASSIFICATION;
    final var identifier = classification.getIdentifier();
    final var name = classification.getName();

    // Find classification with lowercase identifier
    var newClassification = this.classificationDao
      .findClassificationByIdentifier(identifier.toLowerCase())
      .orElse(null);

    // Verify retrieved classification data
    assertNotNull(newClassification);
    assertEquals(identifier, newClassification.getIdentifier());
    assertEquals(name, newClassification.getName());
    assertEquals(classification, newClassification);

    // Find classification with uppercase identifier
    newClassification = this.classificationDao
      .findClassificationByIdentifier(identifier.toUpperCase())
      .orElse(null);

    // Verify retrieved classification data (case-insensitive)
    assertNotNull(newClassification);
    assertEquals(identifier, newClassification.getIdentifier());
    assertEquals(name, newClassification.getName());
    assertEquals(classification, newClassification);
  }

  /**
   * Test case (Order=5) to verify finding a {@link Classification} by name
   * (case-insensitive).
   */
  @Order(5)
  @DisplayName("Find Classification By Name (Case-Insensitive)")
  @Test
  void findClassificationByName() {
    // Insert a test classification
    this.insertClassification();

    // Get the test classification data
    final var classification = ClassificationTest.TEST_CLASSIFICATION;
    final var identifier = classification.getIdentifier();
    final var name = classification.getName();

    // Find classification with lowercase name
    var newClassification = this.classificationDao
      .findClassificationByName(name.toLowerCase())
      .orElse(null);

    // Verify retrieved classification data
    assertNotNull(newClassification);
    assertEquals(identifier, newClassification.getIdentifier());
    assertEquals(name, newClassification.getName());
    assertEquals(classification, newClassification);

    // Find classification with uppercase name
    newClassification = this.classificationDao
      .findClassificationByName(name.toUpperCase())
      .orElse(null);

    // Verify retrieved classification data (case-insensitive)
    assertNotNull(newClassification);
    assertEquals(identifier, newClassification.getIdentifier());
    assertEquals(name, newClassification.getName());
    assertEquals(classification, newClassification);
  }
}
