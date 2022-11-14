package me.kvdpxne.covilo.rest.api.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val USER_CONTROLLER_PATH = "api/v1/user"

@RestController
@RequestMapping(produces = [APPLICATION_JSON_VALUE])
class UserController {

  @GetMapping("user")
  fun test(): String {
    return "Hello, User!"
  }

  @GetMapping("admin")
  fun admin(): String {
    return "Hello, Admin!"
  }
}