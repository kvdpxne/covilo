package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.CrimeClassificationsDto
import me.kvdpxne.covilo.api.rest.dto.toDto
import me.kvdpxne.covilo.domain.persistence.CrimeClassificationRepository
import org.springframework.beans.factory.annotation.Autowired
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
class CrimeClassificationController @Autowired(required = true) constructor(
  private val repository: CrimeClassificationRepository
) {

  @GetMapping(path = ["all", "everything"])
  fun getAll(): ResponseEntity<CrimeClassificationsDto> {
    val l0 = repository.findAll().toDto()
    return ResponseEntity.ok(l0)
  }
}
