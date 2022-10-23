//package me.kvdpxne.covilo.infrastructure.jdbc.dao
//
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import java.util.UUID
//
//@SpringBootTest
//class LocationCityDaoTest @Autowired(required = true) constructor(
//  private val repository: LocationCityDao
//) {
//
//  @Test
//  fun tryFindByIdentifier() {
//    val identifier = UUID.fromString("0fb7ddc0-8d7f-4587-8483-7c70716deac2")
//    val entity = repository.findByIdentifier(identifier)
//    Assertions.assertNotNull(entity)
//  }
//}