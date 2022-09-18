package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.LocationCitiesDto
import me.kvdpxne.covilo.api.rest.dto.LocationCityDto
import me.kvdpxne.covilo.api.rest.dto.toDto
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_REGION
import me.kvdpxne.covilo.domain.persistence.LocationCityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val P0 = COLUMN_COUNTRY
private const val P1 = COLUMN_REGION
private const val P2 = COLUMN_CITY

@RestController
@RequestMapping(
  path = ["api/v1/location/city"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class LocationCityController @Autowired(required = true) constructor(
  private val repository: LocationCityRepository
) {

  /**
   * @param p0 optional country key
   * @param p1 optional region key
   */
  @GetMapping(path = ["all", "everything"])
  fun getAll(
    @RequestParam(name = P0, required = false, defaultValue = "")
    p0: String = "",
    @RequestParam(name = P1, required = false, defaultValue = "")
    p1: String = ""
  ): ResponseEntity<LocationCitiesDto> {
    val b0 = p0.isEmpty()
    val b1 = p1.isEmpty()
    var l0 = repository.findAll()
    if (b0 && b1) {
      return ResponseEntity.ok(l0.toDto())
    }
    l0 = l0.filter {
      val v0 = it.countryKey
      val v1 = it.regionKey
      when {
        !b0 && !b1 -> p0 == v0 && p1 == v1
        !b0 && b1 -> p0 == v0
        else -> true
      }
    }
    return ResponseEntity.ok(l0.toDto())
  }

  /**
   * @param p0 required country key
   * @param p1 required region key
   * @param p2 required city key
   */
  @GetMapping(path = ["one"])
  fun getOne(
    @RequestParam(name = P0, required = true, defaultValue = "")
    p0: String = "",
    @RequestParam(name = P1, required = true, defaultValue = "")
    p1: String = "",
    @RequestParam(name = P2, required = true, defaultValue = "")
    p2: String = ""
  ): ResponseEntity<LocationCityDto> {
    val b0 = p0.isEmpty()
    val b1 = p1.isEmpty()
    val b2 = p2.isEmpty()
    if (b0 || b1 || b2) {
      return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
    val one = repository.findAllByKey(p2).find {
      it.regionKey == p1
    } ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok(one.toDto())
  }
}