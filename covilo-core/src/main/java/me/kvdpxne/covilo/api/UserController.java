package me.kvdpxne.covilo.api;

import jakarta.servlet.http.HttpServletRequest;
import me.kvdpxne.covilo.domain.model.Token;
import me.kvdpxne.covilo.domain.persistence.TokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/0.1.0/")
public class UserController {

  private final TokenRepository tokenRepository;

  public UserController(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @GetMapping("user/me")
  public UserDto me(HttpServletRequest req) {
    final var token = req.getHeader(HttpHeaders.AUTHORIZATION)
        .substring("Bearer ".length());

    return tokenRepository.findByToken(token)
      .map(Token::getUser)
      .map(it -> new UserDto(
        it.getIdentifier(),
        it.getEmail(),
        it.getEmail(),
        it.getGender(),
        it.getBirthDate()
      ))
      .orElse(null);
  }
}
