package me.kvdpxne.covilo.rest.api.controller

import me.kvdpxne.covilo.domain.repositories.CrimeClassificationRepository
import me.kvdpxne.covilo.rest.api.dto.CrimeClassificationsDto
import me.kvdpxne.covilo.rest.api.dto.toDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
  path = ["api/v1/crime/classification"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CrimeClassificationController(
  private val repository: CrimeClassificationRepository
) {

  @GetMapping(path = ["all", "everything"])
  fun getAll(): ResponseEntity<CrimeClassificationsDto> {
    val l0 = repository.findAll().toDto()
    return ResponseEntity.ok(l0)
  }
}
