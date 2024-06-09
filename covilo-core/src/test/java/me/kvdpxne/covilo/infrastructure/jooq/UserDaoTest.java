package me.kvdpxne.covilo.infrastructure.jooq;

import me.kvdpxne.covilo.domain.models.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(1)
@SpringBootTest
final class UserDaoTest {

  private final UserDao userDao;

  @Autowired
  UserDaoTest(
    final UserDao userDao
  ) {
    this.userDao = userDao;
  }

  @AfterEach
  void tearDown() {
    this.userDao.deleteAllUsers();
    assertEquals(0, this.userDao.countUsers());
  }

  @Order(1)
  @DisplayName("Insert Single User")
  @Test
  void insertUser() {
    //
    final var user = UserTest.TEST_USER;

    //
    this.userDao.insertUser(user);

    //
    assertEquals(1, this.userDao.countUsers());
  }

  @Order(2)
  @DisplayName("Insert Multiple Users")
  @Test
  void insertMultipleUsers() {

  }

  @Order(3)
  @DisplayName("Insert and Retrieve User")
  @Test
  void insertUserAndReturn() {
    //
    final var user = UserTest.TEST_USER;
    final var identifier = user.getIdentifier();
    final var email = user.getName();
    final var password = user.getPassword();
    final var firstName = user.getFirstName();
    final var lastName = user.getLastName();
    final var gender = user.getGender();
    final var birthDate = user.getBirthDate();
    final var createdDate = user.getCreatedDate();
    final var lastModifiedDate = user.getLastModifiedDate();

    //
    final var newUser = this.userDao.insertUserAndReturn(user);

    //
    assertEquals(1, this.userDao.countUsers());
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertEquals(email, newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotEquals(createdDate, newUser.getCreatedDate());
    assertEquals(lastModifiedDate, newUser.getLastModifiedDate());
  }

  @Order(4)
  @DisplayName("Update User by Identifier (Case-Insensitive)")
  @Test
  void findUserByIdentifier() {
    //
    this.insertUser();

    //
    final var user = UserTest.TEST_USER;
    final var identifier = user.getIdentifier();
    final var email = user.getName();
    final var password = user.getPassword();
    final var firstName = user.getFirstName();
    final var lastName = user.getLastName();
    final var gender = user.getGender();
    final var birthDate = user.getBirthDate();
    final var createdDate = user.getCreatedDate();
    final var lastModifiedDate = user.getLastModifiedDate();

    //
    var newUser = this.userDao
      .findUserByIdentifier(identifier.toLowerCase())
      .orElse(null);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertEquals(email, newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotEquals(createdDate, newUser.getCreatedDate());
    assertEquals(lastModifiedDate, newUser.getLastModifiedDate());

    //
    newUser = this.userDao
      .findUserByIdentifier(identifier.toUpperCase())
      .orElse(null);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertEquals(email, newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotEquals(createdDate, newUser.getCreatedDate());
    assertEquals(lastModifiedDate, newUser.getLastModifiedDate());
  }

  @Order(5)
  @DisplayName("Update User by Email (Case-Insensitive)")
  @Test
  void findUserByEmail() {
    //
    this.insertUser();

    //
    final var user = UserTest.TEST_USER;
    final var identifier = user.getIdentifier();
    final var email = user.getName();
    final var password = user.getPassword();
    final var firstName = user.getFirstName();
    final var lastName = user.getLastName();
    final var gender = user.getGender();
    final var birthDate = user.getBirthDate();
    final var createdDate = user.getCreatedDate();
    final var lastModifiedDate = user.getLastModifiedDate();

    //
    var newUser = this.userDao
      .findUserByEmail(email.toLowerCase())
      .orElse(null);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertEquals(email, newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotEquals(createdDate, newUser.getCreatedDate());
    assertEquals(lastModifiedDate, newUser.getLastModifiedDate());

    //
    newUser = this.userDao
      .findUserByEmail(email.toLowerCase())
      .orElse(null);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertEquals(email, newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotEquals(createdDate, newUser.getCreatedDate());
    assertEquals(lastModifiedDate, newUser.getLastModifiedDate());
  }

  @Order(6)
  @DisplayName("Update User")
  @Test
  void updateUser() {
    //
    this.insertUser();

    //
    final var user = UserTest.TEST_USER;
    final var identifier = user.getIdentifier();
    final var email = user.getName();
    final var password = user.getPassword();
    final var firstName = user.getFirstName();
    final var lastName = user.getLastName();
    final var gender = user.getGender();
    final var birthDate = user.getBirthDate();

    //
    final var updatedEmail = "UPDATED@gmail.com";
    final var updatedUser = user.toBuilder()
      .withEmail(updatedEmail)
      .build();

    //
    this.userDao.updateUser(updatedUser);

    //
    final var newUser = this.userDao
      .findUserByIdentifier(identifier)
      .orElse(null);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertNotEquals(email, newUser.getName());
    assertNotEquals(updatedEmail, newUser.getName());
    assertEquals(updatedEmail.toLowerCase(), newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotNull(newUser.getLastModifiedDate());
  }

  @Order(7)
  @DisplayName("Update and Retrieve User")
  @Test
  void updateUserAndRetrieve() {
    //
    this.insertUser();

    //
    final var user = UserTest.TEST_USER;
    final var identifier = user.getIdentifier();
    final var email = user.getName();
    final var password = user.getPassword();
    final var firstName = user.getFirstName();
    final var lastName = user.getLastName();
    final var gender = user.getGender();
    final var birthDate = user.getBirthDate();

    //
    final var updatedEmail = "UPDATED@gmail.com";
    final var updatedUser = user.toBuilder()
      .withEmail(updatedEmail)
      .build();

    //
    final var newUser = this.userDao
      .updateUserAndReturn(updatedUser);

    //
    assertNotNull(newUser);
    assertEquals(identifier, newUser.getIdentifier());
    assertNotEquals(email, newUser.getName());
    assertNotEquals(updatedEmail, newUser.getName());
    assertEquals(updatedEmail.toLowerCase(), newUser.getName());
    assertEquals(password, newUser.getPassword());
    assertEquals(firstName, newUser.getFirstName());
    assertEquals(lastName, newUser.getLastName());
    assertEquals(gender, newUser.getGender());
    assertEquals(birthDate, newUser.getBirthDate());
    assertNotNull(newUser.getCreatedDate());
    assertNotNull(newUser.getLastModifiedDate());
  }
}
