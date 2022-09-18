package me.kvdpxne.covilo.infrastructure.security

import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class UserAgentWebAuthenticationDetailsSource :
  WebAuthenticationDetailsSource() {

  override fun buildDetails(context: HttpServletRequest): WebAuthenticationDetails {
    return UserAgentWebAuthenticationDetails(context)
  }
}