package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.infrastructure.jwt.JWT_TOKEN_PREFIX
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST

class AuthorizationFilter(
  private val jwtConfiguration: JwtConfiguration,
  private val userDetailsService: MyUserDetailsService,
  authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filter: FilterChain
  ) {
    val header = request.getHeader(AUTHORIZATION)
    if (header.isNullOrBlank() || !header.startsWith(JWT_TOKEN_PREFIX)) {
      filter.doFilter(request, response)
      return
    }
    val token = header.removePrefix(JWT_TOKEN_PREFIX)
    if (token.isBlank()) {
      response.sendError(SC_BAD_REQUEST, "Invalid JWT token.")
      return
    }
    val email = jwtConfiguration.decodeToken(token)
    val principal = userDetailsService.loadUserByUsername(email)
    val authenticationToken = UsernamePasswordAuthenticationToken(
      principal.username, null, principal.authorities
    )
    val context = SecurityContextHolder.getContext()
    if (null == context.authentication) {
      context.authentication = authenticationToken
    }
    filter.doFilter(request, response)
  }
}