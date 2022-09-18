package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.CrimesDto
import me.kvdpxne.covilo.api.rest.dto.toDto
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_REGION
import me.kvdpxne.covilo.domain.persistence.CrimeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val P0 = COLUMN_COUNTRY
private const val P3 = COLUMN_REGION
private const val P2 = COLUMN_CITY
private const val P4 = COLUMN_CLASSIFICATION

@RestController
@RequestMapping(
  path = ["api/v1/crime"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CrimeController @Autowired(required = true) constructor(
  private val repository: CrimeRepository
) {

  /**
   * @param p0 optional country key
   * @param p1 optional region key
   * @param p2 optional city key
   * @param p3 optional filter by classification key
   */
  @GetMapping(path = ["all", "everything"])
  fun getAll(
    @RequestParam(name = P0, required = false, defaultValue = "")
    p0: String = "",
    @RequestParam(name = P3, required = false, defaultValue = "")
    p1: String = "",
    @RequestParam(name = P2, required = false, defaultValue = "")
    p2: String = "",
    @RequestParam(name = P4, required = false, defaultValue = "")
    p3: String = ""
  ): ResponseEntity<CrimesDto> {
    val b0 = p0.isEmpty()
    val b1 = p1.isEmpty()
    val b2 = p2.isEmpty()
    val b3 = p3.isEmpty()
    var l0 = repository.findAll()
    if (b0 && b1 && b2 && b3) {
      return ResponseEntity.ok(l0.toDto())
    }
    if (!b3) {
      l0 = l0.filter {
        p3 == it.classification.key
      }
    }
    l0 = l0.filter {
      val v0 = it.city.countryKey
      val v1 = it.city.regionKey
      val v2 = it.city.key
      when {
        !b0 && !b1 && !b2 -> p0 == v0 && p1 == v1 && p2 == v2
        !b0 && !b1 && b2 -> p0 == v0 && p1 == v1
        !b0 && b1 && b2 -> p0 == v0
        else -> true
      }
    }
    return ResponseEntity.ok(l0.toDto())
  }
}
