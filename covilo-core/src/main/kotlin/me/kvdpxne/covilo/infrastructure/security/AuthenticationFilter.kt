package me.kvdpxne.covilo.infrastructure.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter : UsernamePasswordAuthenticationFilter() {

  private val objectMapper = jacksonObjectMapper()

  override fun attemptAuthentication(
    request: HttpServletRequest,
    response: HttpServletResponse
  ): Authentication {
    val content = request.reader.use { it.readText() }
    val credentials = objectMapper.readValue<LoginCredentials>(content)
    val token = UsernamePasswordAuthenticationToken(
      credentials.email,
      credentials.password
    )
    setDetails(request, token)
    return authenticationManager.authenticate(token)
  }
}