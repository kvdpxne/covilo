package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.service.UserLifecycleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val AUTHENTICATION_CONTROLLER_PATH = "api/authentication"

@RestController(
  //
  value = "authenticationController"
)
@RequestMapping(
  path = [AUTHENTICATION_CONTROLLER_PATH],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AuthenticationController @Autowired constructor(
  private val userLifecycle: UserLifecycleService,
  private val authenticationManager: AuthenticationManager
) {

  @PostMapping(path = ["signup", "register"])
  fun register(@RequestBody user: User): Map<String, String> {
    val newUser = userLifecycle.createUser(user)
    val token = JwtUtils.generateToken(newUser.email)
    return mapOf("token" to token)
  }

  @PostMapping(path = ["login"])
  fun login(@RequestBody credentials: LoginCredentials): Map<String, String> {
    val authentication = UsernamePasswordAuthenticationToken(
      credentials.email, credentials.password
    )
    authenticationManager.authenticate(authentication)
    val token = JwtUtils.generateToken(credentials.email)
    return mapOf("token" to token)
  }

  @PostMapping(path = ["logout"])
  fun logout() {

  }
}