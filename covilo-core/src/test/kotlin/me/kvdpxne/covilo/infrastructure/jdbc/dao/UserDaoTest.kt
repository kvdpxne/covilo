package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
class UserDaoTest @Autowired constructor(
  private val repository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) {

  internal val user: User
    get() = User(
      identifier = UUID.fromString("9c8a7bee-cd81-413c-8d4f-be345ca1de8b"),
      email = "example.user@covilo.com",
      password = passwordEncoder.encode("covilo.user"),
      firstName = "Stefan",
      lastName = "Malinowski",
      birthDate = LocalDate.of(2000, 6, 24),
      createdDate = LocalDateTime.now(),
      lastModifiedDate = null
    )

  @Test
  @Order(value = Int.MIN_VALUE)
  fun tryInsert() {
    repository.insert(user)
  }

  @Test
  @Order(value = 0)
  fun tryFindByIdentifier() {
    val identifier = user.identifier
    val entity = repository.findByIdentifier(identifier)
    Assertions.assertNotNull(entity)
  }

  @Test
  @Order(value = 1)
  fun tryFindByEmail() {
    val email = user.email
    val entity = repository.findByEmail(email)
    Assertions.assertNotNull(entity)
  }

  @Test
  @Order(value = 2)
  fun tryFindAll() {
    val entities = repository.findAll()
    Assertions.assertTrue(entities.isNotEmpty())
  }

  @Test
  @Order(value = 3)
  fun tryDelete() {
    val identifier = user.identifier
    repository.delete(identifier)
  }
}