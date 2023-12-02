package me.kvdpxne.covilo.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public final class TokenLogoutHandler
  implements LogoutHandler {

  private final ITokenRepository tokenRepository;

  /**
   * Causes a logout to be completed.
   */
  @Override
  public void logout(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final Authentication authentication
  ) {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String prefix = Constants.PREFIX;
    if (null == header || !header.startsWith(prefix)) {
      return;
    }
    final String compactToken = header.substring(prefix.length());
    this.tokenRepository.findTokenByCompactToken(compactToken).ifPresent(it -> {
      this.tokenRepository.deleteToken(it);
      SecurityContextHolder.clearContext();
    });
  }
}
