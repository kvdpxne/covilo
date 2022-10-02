package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.LocationCountry
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
class LocationCountryDaoTest @Autowired(required = true) constructor(
  private val repository: LocationCountryDao
) {

  companion object {

    internal val LOCATION_COUNTRY: LocationCountry
      get() = LocationCountry(
        identifier = UUID.fromString("ad7901b1-a232-46be-969a-e6ff7d57ea54"),
        key = "norway"
      )
  }

  @Test
  @Order(value = Int.MIN_VALUE)
  fun tryInsert() {
    val entity = LOCATION_COUNTRY
    repository.insert(entity)
  }

  @Test
  @Order(value = 0)
  fun tryFindByIdentifier() {
    val identifier = LOCATION_COUNTRY.identifier
    val entity = repository.findByIdentifier(identifier)
    Assertions.assertNotNull(entity)
  }

  @Test
  @Order(value = 1)
  fun tryFindByKey() {
    val key = LOCATION_COUNTRY.key
    val entity = repository.findByKey(key)
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
    val identifier = LOCATION_COUNTRY.identifier
    repository.delete(identifier)
  }

  @Test
  @Order(value = 4)
  fun tryFindByIdentifierOrNull() {
    val identifier = LOCATION_COUNTRY.identifier
    val entity = repository.findByIdentifier(identifier)
    Assertions.assertNull(entity)
  }
}