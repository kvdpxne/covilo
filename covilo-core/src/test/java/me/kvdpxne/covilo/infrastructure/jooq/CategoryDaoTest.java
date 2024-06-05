package me.kvdpxne.covilo.infrastructure.jooq;

import org.junit.jupiter.api.AfterEach;
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
}
