package me.kvdpxne.covilo.domain.models;

import java.time.LocalDate;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.User;
import org.jooq.generated.tables.UserTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 *
 */
@Order(100)
public final class UserTest {

  /**
   *
   */
  public static final User TEST_USER =
    UserTest.makeTestUser();

  /**
   *
   */
  public static User makeTestUser() {
    try {
      Thread.sleep(3L);
    } catch (final InterruptedException exception) {
      throw new RuntimeException(exception);
    }

    return User.builder()
      .withRandomIdentifier()
      .withEmail("wioleta.klim@gmail.com")
      .withPassword("testP@ssw0rd")
      .withFirstName("Wioleta")
      .withLastName("Klim")
      .withGender(Gender.FEMALE)
      .withBrithDate(LocalDate.of(2000, 1, 1))
      .build();
  }

  //
  UserTest() {
    // ...
  }

  @Test
  void fs() {
    //
    final var user = UserTest.TEST_USER;
    final var email = user.getName();

    final var newUser = user.toBuilder()
      .withEmail("wioleta.klim+covilo@gmail.com")
      .build();
  }

  @DisplayName("User Equals (Case-Insensitive)")
  @Test
  void equalsUser() {
    final var user = TEST_USER;

    final var secondUser = user.toBuilder()
      .withEmail(user.getName().toUpperCase())
      .withFirstName(user.getFirstName().toUpperCase())
      .withLastName(user.getLastName().toUpperCase())
      .build();


  }
}
