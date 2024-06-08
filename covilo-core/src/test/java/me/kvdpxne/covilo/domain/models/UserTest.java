package me.kvdpxne.covilo.domain.models;

import java.time.LocalDate;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.User;
/**
 *
 */
public final class UserTest {

  /**
   *
   */
  public static final User TEST_USER;

  static {
    TEST_USER = User.builder()
      .withRandomIdentifier()
      .withEmail("wioleta.klim@mail.to")
      .withPassword("testP@ssw0rd")
      .withFirstName("Wioleta")
      .withLastName("Klim")
      .withGender(Gender.FEMALE)
      .withBrithDate(LocalDate.of(2000, 1, 1))
      .withCreatedDate()
      .build();
  }

  //
  UserTest() {
    // ...
  }
}
