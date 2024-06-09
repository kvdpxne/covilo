package me.kvdpxne.covilo.infrastructure.jooq;

import me.kvdpxne.covilo.domain.models.CategoryTest;
import me.kvdpxne.covilo.domain.models.CrimeTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(3)
@SpringBootTest
public final class CrimeDaoTest {

  /**
   *
   */
  private final CrimeDao crimeDao;

  /**
   *
   */
  private final CategoryDao categoryDao;

  /**
   *
   */
  private final UserDao userDao;

  /**
   *
   */
  private final ClassificationDao classificationDao;

  /**
   *
   */
  @Autowired
  CrimeDaoTest(
    final CrimeDao crimeDao,
    final CategoryDao categoryDao,
    final UserDao userDao,
    final ClassificationDao classificationDao
  ) {
    this.crimeDao = crimeDao;
    this.categoryDao = categoryDao;
    this.userDao = userDao;
    this.classificationDao = classificationDao;
  }

  /**
   *
   */
  @AfterEach
  void tearDown() {
    this.crimeDao.deleteAllCrimes();
    this.categoryDao.deleteAllCategories();
    this.userDao.deleteAllUsers();
    this.classificationDao.deleteAllClassifications();
  }

  @Order(0)
  @DisplayName("Insert Single Crime")
  @Test
  void insertCrime() {
    // Get the test crime data
    final var crime = CrimeTest.TEST_CRIME;

    // Insert the classification
    this.crimeDao.insertCrime(crime);

    // Verify that one crime record is present
    assertEquals(1, this.crimeDao.countCrimes());
  }
}
