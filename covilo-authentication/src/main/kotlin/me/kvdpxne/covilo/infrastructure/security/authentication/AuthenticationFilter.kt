package me.kvdpxne.covilo.infrastructure.security.authentication

import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST

private const val TOKEN_PREFIX = "Bearer"

class AuthenticationFilter(
  private val configuration: JwtConfiguration,
  private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filter: FilterChain
  ) {
    val header = request.getHeader(AUTHORIZATION)
    if (header.isNullOrBlank() || !header.startsWith(TOKEN_PREFIX)) {
      filter.doFilter(request, response)
      return
    }
    val token = header.removePrefix(TOKEN_PREFIX)
    if (token.isBlank()) {
      response.sendError(SC_BAD_REQUEST, "Invalid JWT token.")
      return
    }
    val email = configuration.decode(token)
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