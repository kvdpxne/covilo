package me.kvdpxne.covilo.infrastructure.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.kvdpxne.covilo.domain.persistence.UserRepository
import me.kvdpxne.covilo.domain.service.AuthenticationTokenLifecycle
import me.kvdpxne.covilo.infrastructure.jwt.JWT_TOKEN_PREFIX
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import me.kvdpxne.covilo.infrastructure.jwt.JwtResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationSuccessHandler(
  private val jwtConfiguration: JwtConfiguration,
  private val authenticationTokenLifecycle: AuthenticationTokenLifecycle,
  private val userRepository: UserRepository
) : SimpleUrlAuthenticationSuccessHandler() {

  private val mapper = jacksonObjectMapper()

  override fun onAuthenticationSuccess(
    request: HttpServletRequest,
    response: HttpServletResponse,
    authentication: Authentication
  ) {
    val principal = authentication.principal as MyPrincipal
    val user = userRepository.findByEmail(principal.username) ?: return
    val token = jwtConfiguration.createToken(user.email)
    // TODO fix
    val authenticationToken = authenticationTokenLifecycle.create(user)
    val tokenResponse = JwtResponse(token, authenticationToken.token)
    val content = mapper.writeValueAsString(tokenResponse)
    response.addHeader(AUTHORIZATION, "$JWT_TOKEN_PREFIX $token")
    response.addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
    response.writer.use { it.write(content) }
  }
}