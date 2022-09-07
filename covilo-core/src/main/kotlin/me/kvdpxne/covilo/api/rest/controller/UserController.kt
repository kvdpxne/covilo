package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.api.rest.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
  path = ["api/v1/user"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UserController @Autowired(required = true) constructor() {

  @GetMapping(path = ["me"])
  fun getMe(): UserDto {
    TODO("Not yet implemented")
  }
}