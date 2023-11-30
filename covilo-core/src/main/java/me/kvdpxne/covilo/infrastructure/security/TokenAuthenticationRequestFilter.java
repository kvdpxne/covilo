package me.kvdpxne.covilo.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.ITokenService;
import me.kvdpxne.covilo.application.exception.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class TokenAuthenticationRequestFilter
  extends OncePerRequestFilter {

  private final ITokenService tokenService;
  private final UserAccountDetailsService userDetailsService;

  /**
   * Same contract as for doFilter, but guaranteed to be just invoked once per
   * request within a single request thread. See shouldNotFilterAsyncDispatch()
   * for details.
   */
  @Override
  public void doFilterInternal(
    HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    //
    if (request.getServletPath().contains(Constants.PATH)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (null != SecurityContextHolder.getContext().getAuthentication()) {
      filterChain.doFilter(request, response);
      return;
    }

    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String prefix = Constants.PREFIX;

    if (null == header || !header.startsWith(prefix)) {
      filterChain.doFilter(request, response);
      return;
    }

    final String compactToken = header.substring(prefix.length());

    // Subject of the JWT (the user).
    final String subject;

    // Recipient for which the JWT is intended.
    final UUID audience;

    try {
      subject = this.tokenService.extractSubject(compactToken);
      audience = UUID.fromString(
        this.tokenService.extractAudience(compactToken)
      );
    } catch (final TokenException exception) {
      filterChain.doFilter(request, response);
      return;
    }

    if (null == subject) {
      filterChain.doFilter(request, response);
      return;
    }

    final UserAccountDetails principal = (UserAccountDetails)
      this.userDetailsService.loadUserByUsername(subject);

    if (principal.user().identifier().equals(audience)) {
      var authToken = new UsernamePasswordAuthenticationToken(
        principal,
        null,
        principal.getAuthorities()
      );
      authToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request)
      );
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    filterChain.doFilter(request, response);
  }
}
