package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.persistence.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
class UserDaoTest @Autowired(required = true) constructor(
  private val repository: UserRepository
) {

  @Test
  @Order(value = 0)
  fun tryFindByIdentifier() {
    val identifier = UUID.fromString("bb857ce8-ce6e-4e07-8258-22ff9ee2d0aa")
    val entity = repository.findByIdentifier(identifier)
    Assertions.assertNotNull(entity)
  }

  @Test
  @Order(value = 1)
  fun tryFindByEmail() {
    val email = "administrator@covilo.com"
    val entity = repository.findByEmail(email)
    Assertions.assertNotNull(entity)
  }
}