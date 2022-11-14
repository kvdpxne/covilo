package me.kvdpxne.covilo.infrastructure.security.authentication.controllers

import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.services.AccountManagementService
import me.kvdpxne.covilo.domain.services.RefreshTokenLifecycle
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import me.kvdpxne.covilo.infrastructure.jwt.RefreshTokenRenewResponse
import me.kvdpxne.covilo.infrastructure.jwt.RefreshTokenRequest
import me.kvdpxne.covilo.infrastructure.jwt.TokenResponse
import me.kvdpxne.covilo.infrastructure.security.authentication.BasicUserDetails
import me.kvdpxne.covilo.infrastructure.security.authentication.dto.LoginCredentialsDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller(value = "authentication-controller")
@RequestMapping(
  path = ["api/v1/authentication"],
  produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AuthenticationController @Autowired(required = true) constructor(
  private val authenticationManager: AuthenticationManager,
  private val jwtConfiguration: JwtConfiguration,
  private val accountManagementService: AccountManagementService,
  private val refreshTokenLifecycle: RefreshTokenLifecycle
) {

  companion object {
    private val ALREADY_USE_EMAIL = ResponseEntity.badRequest().body(
      "error: email is already in use!"
    )
  }

  @PostMapping(path = ["signup"])
  fun signup(
    @RequestBody(required = true) account: Account
  ): ResponseEntity<out Any> {
    if (accountManagementService.isExists(account.email)) {
      return ALREADY_USE_EMAIL
    }
    val newAccount = accountManagementService.create(account)
    return ResponseEntity.accepted().build()
  }

  @PostMapping(path = ["login"])
  fun login(
    @RequestBody(required = true) credentials: LoginCredentialsDto
  ): ResponseEntity<out Any> {
    val email = credentials.email
    val password = credentials.password
    val authentication = authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(email, password)
    )
    SecurityContextHolder.getContext().authentication = authentication
    val principal = authentication.principal as BasicUserDetails
    println(principal)
    val token = jwtConfiguration.create(email)
    val account = principal.account
    val refreshToken = refreshTokenLifecycle.create(principal.account)
    val response = TokenResponse(token, refreshToken.token, account.identifier, email, account.roles)
    return ResponseEntity.ok(response)
  }

  @PostMapping(path = ["refresh-token"])
  fun refreshToken(
    @RequestBody(required = true) request: RefreshTokenRequest
  ): ResponseEntity<RefreshTokenRenewResponse> {
    val token = request.refreshToken
    val refreshToken = refreshTokenLifecycle.find(token)
    jwtConfiguration.verify(refreshToken.token)
    val email = refreshToken.account.email
    val renewToken = jwtConfiguration.create(email)
    val response = RefreshTokenRenewResponse(token, renewToken)
    return ResponseEntity.ok(response)
  }

  @PostMapping(path = ["logout"])
  fun logout(
    @RequestBody(required = true) email: String
  ): ResponseEntity<Nothing> {
    val account = accountManagementService.find(email)
    refreshTokenLifecycle.delete(account)
    return ResponseEntity.accepted().build()
  }
}