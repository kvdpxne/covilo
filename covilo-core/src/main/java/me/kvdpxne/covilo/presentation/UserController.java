package me.kvdpxne.covilo.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import me.kvdpxne.covilo.infrastructure.security.TokenAuthenticationRequestFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/0.1.0/")
public class UserController {

  private final ITokenRepository tokenRepository;
  private final IUserMapper userMapper;

  @GetMapping("user/me")
  public ResponseEntity<UserDto> me(
    final HttpServletRequest request
  ) {
    final String compactToken = request.getHeader(HttpHeaders.AUTHORIZATION)
      .substring(TokenAuthenticationRequestFilter.PREFIX.length());

    final User user = this.tokenRepository.findUserByCompactTokenOrNull(compactToken);
    if (null == user) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(
      this.userMapper.toUserDto(user)
    );
  }
}
