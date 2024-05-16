package me.kvdpxne.covilo.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.persistence.UserRepository;
import me.kvdpxne.covilo.infrastructure.security.jwt.JjwtServiceExtension;
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

  private final JjwtServiceExtension jwtService;

  private final UserRepository userRepository;

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
    if (request.getServletPath().contains(Endpoints.USER_AUTHENTICATION)) {
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

    var claims = this.jwtService.readJws(compactToken);

    final Date expiryAt = claims.getExpiration();
    if (null == expiryAt || Instant.now().isAfter(expiryAt.toInstant())) {
      filterChain.doFilter(request, response);
      return;
    }

    // Subject of the JWT (the user).
    final String subject = claims.getSubject();

    if (null == subject) {
      filterChain.doFilter(request, response);
      return;
    }

    final UserAccountDetails principal= this.userRepository
      .findUserByIdentifier(UUID.fromString(subject))
      .map(UserAccountDetails::new)
      .orElse(null);

    if (null == principal) {
      filterChain.doFilter(request, response);
      return;
    }

    var authToken = new UsernamePasswordAuthenticationToken(
      principal,
      null,
      principal.getAuthorities()
    );
    authToken.setDetails(
      new WebAuthenticationDetailsSource().buildDetails(request)
    );
    SecurityContextHolder.getContext().setAuthentication(authToken);
    filterChain.doFilter(request, response);
  }
}
