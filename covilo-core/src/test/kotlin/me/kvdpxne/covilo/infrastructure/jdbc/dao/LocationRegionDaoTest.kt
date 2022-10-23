//package me.kvdpxne.covilo.infrastructure.jdbc.dao
//
//import me.kvdpxne.covilo.domain.models.LocationRegion
//import me.kvdpxne.covilo.domain.persistence.LocationRegionRepository
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
//import org.junit.jupiter.api.Order
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestMethodOrder
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import java.util.UUID
//
//@SpringBootTest
//@TestMethodOrder(OrderAnnotation::class)
//class LocationRegionDaoTest @Autowired(required = true) constructor(
//  private val repository: LocationRegionRepository
//) {
//
//  companion object {
//
//    internal val LOCATION_REGION: LocationRegion
//      get() = LocationRegion(
//        identifier = UUID.fromString("4f82151d-24dd-49f5-8f6b-61adbb7d834c"),
//        key = "oslo",
//        domesticName = "Oslo",
//        country = LocationCountryDaoTest.LOCATION_COUNTRY
//      )
//  }
//
//  @Test
//  @Order(value = Int.MIN_VALUE)
//  fun tryInsert() {
//    val entity = LOCATION_REGION
//    repository.insert(entity)
//  }
//
//  @Test
//  @Order(value = 0)
//  fun tryFindByIdentifier() {
//    val identifier = LOCATION_REGION.identifier
//    val entity = repository.findByIdentifier(identifier)
//    Assertions.assertNotNull(entity)
//  }
//
//  @Test
//  @Order(value = 1)
//  fun tryFindByKey() {
//    val key = LOCATION_REGION.key
//    val entity = repository.findByKey(key)
//    Assertions.assertNotNull(entity)
//  }
//
//  @Test
//  @Order(value = 2)
//  fun tryFindAll() {
//    val entities = repository.findAll()
//    Assertions.assertTrue(entities.isNotEmpty())
//  }
//
//  @Test
//  @Order(value = 3)
//  fun tryDelete() {
//    val identifier = LOCATION_REGION.identifier
//    repository.delete(identifier)
//  }
//
//  @Test
//  @Order(value = 4)
//  fun tryFindByIdentifierOrNull() {
//    val identifier = LOCATION_REGION.identifier
//    val entity = repository.findByIdentifier(identifier)
//    Assertions.assertNull(entity)
//  }
//}