package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.LocationRegionDto
import me.kvdpxne.covilo.api.rest.dto.LocationRegionsDto
import me.kvdpxne.covilo.api.rest.dto.toDto
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.persistence.LocationRegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val P0 = TABLE_LOCATION_COUNTRY
private const val P1 = COLUMN_KEY

@RestController
@RequestMapping(
  path = ["api/v1/location/region"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class LocationRegionController @Autowired(required = true) constructor(
  private val repository: LocationRegionRepository,
) {

  /**
   * @param p0 optional country key
   */
  @GetMapping(path = ["all", "everything"])
  fun getAll(
    @RequestParam(name = P0, required = false, defaultValue = "")
    p0: String = ""
  ): ResponseEntity<LocationRegionsDto> {
    val b0 = p0.isEmpty()
    var l0 = repository.findAll()
    if (b0) {
      return ResponseEntity.ok(l0.toDto())
    }
    l0 = l0.filter {
      p0 == it.countryKey
    }
    return ResponseEntity.ok(l0.toDto())
  }

  /**
   * @param p0 required region key
   */
  @GetMapping(path = ["one"])
  fun getOne(
    @RequestParam(name = P1, required = true, defaultValue = "")
    p0: String = ""
  ): ResponseEntity<LocationRegionDto> {
    if (p0.isEmpty()) {
      return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
    val s0 = repository.findByKey(p0)
      ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok(s0.toDto())
  }
}