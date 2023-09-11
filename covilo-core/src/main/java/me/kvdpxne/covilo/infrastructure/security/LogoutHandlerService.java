package me.kvdpxne.covilo.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.infrastructure.jpa.repository.TokenDao;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class LogoutHandlerService implements LogoutHandler {

  private final TokenDao tokenRepository;

  /**
   * Causes a logout to be completed.
   */
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (null == header || !header.startsWith("Bearer ")) {
      return;
    }

    var token = header.substring(7);
    var storedToken = tokenRepository.findByToken(token).orElse(null);

    if (null == storedToken) {
      return;
    }

    storedToken.setExpired(true);
    storedToken.setRevoked(true);
    tokenRepository.save(storedToken);
    SecurityContextHolder.clearContext();
  }
}
