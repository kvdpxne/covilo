package me.kvdpxne.covilo.api.rest.controller

import me.kvdpxne.covilo.domain.models.AuthenticationToken
import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.service.AuthenticationTokenLifecycle
import me.kvdpxne.covilo.domain.service.UserLifecycle
import me.kvdpxne.covilo.infrastructure.jwt.JwtRefreshTokenRequest
import me.kvdpxne.covilo.infrastructure.jwt.JwtResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/authentication", produces = [APPLICATION_JSON_VALUE])
class AuthenticationController(
  private val userLifecycle: UserLifecycle,
  private val authenticationTokenLifecycle: AuthenticationTokenLifecycle
) {

  @PostMapping(path = ["signup", "register"])
  fun register(@RequestBody user: User): AuthenticationToken {
    val newUser = userLifecycle.createUser(user)
    val authenticationToken = authenticationTokenLifecycle.create(newUser)
    return authenticationToken
  }

  @PostMapping("refresh")
  fun refresh(@RequestBody request: JwtRefreshTokenRequest): JwtResponse {
    return authenticationTokenLifecycle.refresh(request)
  }
}