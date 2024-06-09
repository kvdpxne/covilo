package me.kvdpxne.covilo.infrastructure.jooq;

import java.util.ArrayList;
import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.models.CategoryTest;
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
 * Test class for {@link CategoryDao}.
 */
@Order(2)
@SpringBootTest
final class CategoryDaoTest {

  private static final int FS = 100;

  /**
   * Instance of the {@link CategoryDao} to be tested.
   */
  private final CategoryDao categoryDao;

  private final ClassificationDao classificationDao;

  @Autowired
  CategoryDaoTest(
    final CategoryDao categoryDao,
    final ClassificationDao classificationDao
  ) {
    this.categoryDao = categoryDao;
    this.classificationDao = classificationDao;
  }

  @AfterEach
  void tearDown() {
    this.classificationDao.deleteAllClassifications();
    this.categoryDao.deleteAllCategories();
    assertEquals(0, this.categoryDao.countCategories());
  }

  /**
   * Test case (Order=1) to verify insertion of a single {@link Category}
   * record.
   */
  @Order(1)
  @DisplayName("Insert Single Category")
  @Test
  void insertCategory() {
    // Get the test classification data
    final var category = CategoryTest.TEST_CATEGORY;

    // Insert the classification
    this.categoryDao.insertCategory(category);

    // Verify that one classification record is present
    assertEquals(1, this.categoryDao.countCategories());
  }

  /**
   * Test case (Order=2) to verify insertion of multiple {@link Classification}
   * records.
   */
  @Order(2)
  @DisplayName("Insert Multiple Categories")
  @Test
  void insertMultipleCategories() {
    // Number of categories to insert
    final var size = FS;

    // Create a list to hold the categories
    final var categories = new ArrayList<Category>();

    // Generate classifications with random identifiers and dynamic names
    for (var i = 0; i < size; ++i) {
      categories.add(CategoryTest.makeRandomTestCategory());
    }

    // Insert the classifications
    this.categoryDao.insertCategories(categories);

    // Verify that the expected number of classifications was inserted
    assertEquals(size, this.categoryDao.countCategories());
  }

  /**
   * Test case (Order=3) to verify insertion and retrieval of a
   * {@link Classification} record.
   */
  @Order(3)
  @DisplayName("Insert and Retrieve Classification")
  @Test
  void insertCategoryAndReturn() {
    // Get the test classification data
    final var category = CategoryTest.TEST_CATEGORY;
    final var identifier = category.getIdentifier();
    final var name = category.getName();
    final var classification = category.getClassification();

    //
    this.classificationDao.insertClassification(classification);

    // Insert and retrieve the classification
    final var newCategory = this.categoryDao
      .insertCategoryAndReturn(category);

    // Verify retrieved classification data
    assertEquals(1, this.categoryDao.countCategories());
    assertNotNull(newCategory);
    assertEquals(identifier, newCategory.getIdentifier());
    assertEquals(name, newCategory.getName());
    assertEquals(classification, newCategory.getClassification());
    assertEquals(category, newCategory);
  }

  @Order(4)
  @DisplayName("Find Categories")
  @Test
  void findCategories() {
    //
    this.insertMultipleCategories();

    //
    final var pageable = Pageable.of(0, 10);
    final var page = this.categoryDao.findCategories(pageable);

    assertNotNull(page);
    assertNotNull(page.getContent());
    assertEquals(FS, page.getTotalElements());
  }

  @Order(5)
  @DisplayName("Find Categories by Classification Identifier (Case-Insensitive)")
  @Test
  void findCategoriesByClassificationIdentifier() {
    //
    this.insertMultipleCategories();

    //
    final var classification = ClassificationTest.TEST_CLASSIFICATION;
    final var identifier = classification.getIdentifier();

    //
    var categories = this.categoryDao
      .findCategoriesByClassificationIdentifier(identifier.toLowerCase());

    //
    assertNotNull(categories);
    assertEquals(FS, categories.size());

    //
    categories = this.categoryDao
      .findCategoriesByClassificationIdentifier(identifier.toUpperCase());

    //
    assertNotNull(categories);
    assertEquals(FS, categories.size());
  }

  /**
   * Test case (Order=4) to verify finding a {@link Classification} by
   * identifier (case-insensitive).
   */
  @Order(6)
  @DisplayName("Find Category By Identifier (Case-Insensitive)")
  @Test
  void findCategoryByIdentifier() {
    // Get the test classification data
    final var category = CategoryTest.TEST_CATEGORY;
    final var identifier = category.getIdentifier();
    final var name = category.getName();
    final var classification = category.getClassification();

    //
    this.classificationDao.insertClassification(classification);
    this.insertCategory();

    // Find classification with lowercase identifier
    var newCategory = this.categoryDao
      .findCategoryByIdentifier(identifier.toLowerCase())
      .orElse(null);

    // Verify retrieved classification data
    assertNotNull(newCategory);
    assertEquals(identifier, newCategory.getIdentifier());
    assertEquals(name, newCategory.getName());
    assertEquals(classification, newCategory.getClassification());
    assertEquals(category, newCategory);

    // Find classification with uppercase identifier
    newCategory = this.categoryDao
      .findCategoryByIdentifier(identifier.toUpperCase())
      .orElse(null);

    // Verify retrieved classification data (case-insensitive)
    assertNotNull(newCategory);
    assertEquals(identifier, newCategory.getIdentifier());
    assertEquals(name, newCategory.getName());
    assertEquals(classification, newCategory.getClassification());
    assertEquals(category, newCategory);
  }

  /**
   * Test case (Order=5) to verify finding a {@link Classification} by name
   * (case-insensitive).
   */
  @Order(7)
  @DisplayName("Find Category By Name (Case-Insensitive)")
  @Test
  void findCategoryByName() {
    // Get the test classification data
    final var category = CategoryTest.TEST_CATEGORY;
    final var identifier = category.getIdentifier();
    final var name = category.getName();
    final var classification = category.getClassification();

    //
    this.classificationDao.insertClassification(classification);
    this.insertCategory();

    // Find classification with lowercase name
    var newCategory = this.categoryDao
      .findCategoryByName(name.toLowerCase())
      .orElse(null);

    // Verify retrieved classification data
    assertNotNull(newCategory);
    assertEquals(identifier, newCategory.getIdentifier());
    assertEquals(name, newCategory.getName());
    assertEquals(classification, newCategory.getClassification());
    assertEquals(category, newCategory);

    // Find classification with uppercase name
    newCategory = this.categoryDao
      .findCategoryByName(name.toUpperCase())
      .orElse(null);

    // Verify retrieved classification data (case-insensitive)
    assertNotNull(newCategory);
    assertEquals(identifier, newCategory.getIdentifier());
    assertEquals(name, newCategory.getName());
    assertEquals(classification, newCategory.getClassification());
    assertEquals(category, newCategory);
  }

  @Order(8)
  @DisplayName("Update Single Classification")
  @Test
  void updateCategory() {

  }

  @Order(9)
  @DisplayName("Update Multiple Categories")
  @Test
  void updateCategories() {

  }

  @Order(10)
  @DisplayName("Update and Retrieve Classification")
  @Test
  void updateCategoryAndReturn() {

  }

  @Order(11)
  @DisplayName("Delete Category By Identifier (Case-Insensitive)")
  @Test
  void deleteCategoryByIdentifier() {

  }

  @Order(12)
  @DisplayName("Delete Categories By Identifiers (Case-Insensitive)")
  @Test
  void deleteCategoriesByIdentifiers() {

  }
}
