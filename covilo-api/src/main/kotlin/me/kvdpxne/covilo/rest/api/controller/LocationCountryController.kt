package me.kvdpxne.covilo.rest.api.controller

import me.kvdpxne.covilo.KEY_COLUMN
import me.kvdpxne.covilo.domain.repositories.LocationCountryRepository
import me.kvdpxne.covilo.rest.api.dto.LocationCountriesDto
import me.kvdpxne.covilo.rest.api.dto.LocationCountryDto
import me.kvdpxne.covilo.rest.api.dto.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val P0 = KEY_COLUMN

@RestController
@RequestMapping(
  path = ["api/v1/location/country"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class LocationCountryController @Autowired(required = true) constructor(
  private val repository: LocationCountryRepository
) {

  @GetMapping(path = ["all", "everything"])
  fun getAll(): ResponseEntity<LocationCountriesDto> {
    val l0 = repository.findAll().toDto()
    return ResponseEntity.ok(l0)
  }

  @GetMapping(path = ["one"])
  fun getOne(
    @RequestParam(name = P0, required = true, defaultValue = "")
    p0: String = ""
  ): ResponseEntity<LocationCountryDto> {
    if (p0.isEmpty()) {
      return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
    val s0 = repository.findByKey(p0)
      ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok(s0.toDto())
  }
}