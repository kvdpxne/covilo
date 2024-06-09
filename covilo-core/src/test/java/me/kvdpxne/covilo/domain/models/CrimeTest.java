package me.kvdpxne.covilo.domain.models;

import java.time.OffsetDateTime;
import me.kvdpxne.covilo.domain.model.Crime;
import org.junit.jupiter.api.Order;

@Order(103)
public final class CrimeTest {

  /**
   *
   */
  public static final Crime TEST_CRIME =
    CrimeTest.makeTestCrime();

  /**
   *
   */
  public static Crime makeTestCrime() {
    try {
      Thread.sleep(3L);
    } catch (final InterruptedException exception) {
      throw new RuntimeException(exception);
    }

    return Crime.builder()
      .withRandomIdentifier()
      .withTime(OffsetDateTime.now())
      .withCoordinates(51.34733d, 22.01213)
//      .withCategories()
      .withReporter(UserTest.makeTestUser())
      .withConfirmed(true)
      .build();
  }

  //
  CrimeTest() {
    // ...
  }
}
