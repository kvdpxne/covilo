package me.kvdpxne.covilo.infrastructure.jooq;

import me.kvdpxne.covilo.domain.model.Category;
import me.kvdpxne.covilo.domain.model.Classification;
import me.kvdpxne.covilo.domain.models.CategoryTest;
import me.kvdpxne.covilo.domain.models.ClassificationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link CategoryDao}.
 */
@SpringBootTest
final class CategoryDaoTest {

  /**
   * Instance of the {@link CategoryDao} to be tested.
   */
  private final CategoryDao categoryDao;

  @Autowired
  CategoryDaoTest(
    final CategoryDao categoryDao
  ) {
    this.categoryDao = categoryDao;
  }

  @AfterEach
  void tearDown() {
    this.categoryDao.deleteAllCategories();
    assertEquals(0, this.categoryDao.countCategories());
  }

  /**
   * Test case (Order=1) to verify insertion of a single {@link Category}
   * record.
   */
  @Order(1)
  @DisplayName("Insert Single Classification")
  @Test
  void insertClassification() {
    // Get the test classification data
    final var category = CategoryTest.TEST_CATEGORY;

    // Insert the classification
    this.categoryDao.insertCategory(category);

    // Verify that one classification record is present
    assertEquals(1, this.categoryDao.countCategories());
  }
}
