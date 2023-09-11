package me.kvdpxne.covilo.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.infrastructure.jpa.repository.TokenDao;
import me.kvdpxne.covilo.infrastructure.jwt.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public final class TokenAuthenticationRequestFilter extends OncePerRequestFilter {

  /**
   *
   */
  private static final String AUTHENTICATION_PATH = "/api/0.1.0/auth";

  private final TokenService tokenService;
  private final UserDetailsService userDetailsService;
  private final TokenDao tokenRepository;

  /**
   * Same contract as for doFilter, but guaranteed to be just invoked once per
   * request within a single request thread. See shouldNotFilterAsyncDispatch()
   * for details.
   */
  @Override
  public void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                               @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    if (request.getServletPath().contains(AUTHENTICATION_PATH)) {
      filterChain.doFilter(request, response);
      return;
    }

    //
    if (null != SecurityContextHolder.getContext().getAuthentication()) {
      filterChain.doFilter(request, response);
      return;
    }

    var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (null == header || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = header.substring(7);
    var userEmail = tokenService.extractUsername(token);

    if (null == userEmail) {
      filterChain.doFilter(request, response);
      return;
    }

    var userDetails = userDetailsService.loadUserByUsername(userEmail);

    var isTokenValid = tokenRepository.findByToken(token)
      .map(t -> !t.isExpired() && !t.isRevoked())
      .orElse(false);

    if (tokenService.isTokenValid(token, ((UserAccountDetails) userDetails).getUser()) && isTokenValid) {
      var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    filterChain.doFilter(request, response);
  }
}
