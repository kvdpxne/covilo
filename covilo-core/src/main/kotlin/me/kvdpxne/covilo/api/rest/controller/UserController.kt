package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.UserDto
import me.kvdpxne.covilo.domain.persistence.UserRepository
import me.kvdpxne.covilo.domain.service.UserLifecycleService
import me.kvdpxne.covilo.infrastructure.security.MyPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
  path = ["api/v1/user"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UserController @Autowired(required = true) constructor(
  private val repository: UserRepository,
  private val lifecycle: UserLifecycleService
) {

  @GetMapping(path = ["me"])
  fun getMe(@AuthenticationPrincipal principal: MyPrincipal): UserDto {
    TODO("Not yet implemented")
  }
}