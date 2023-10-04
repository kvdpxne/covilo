package me.kvdpxne.covilo.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.application.payload.UpdateEmailRequest;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import me.kvdpxne.covilo.infrastructure.security.TokenAuthenticationRequestFilter;
import me.kvdpxne.covilo.infrastructure.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "api/0.1.0/me")
@RestController
public final class UserMeController {

  private final ITokenRepository tokenRepository;
  private final IUserMapper userMapper;
  private final StorageService storageService;

  private User getUserByCompactToken(
    final HttpServletRequest request
  ) throws UserNotFoundException {
    final String compactToken = request.getHeader(HttpHeaders.AUTHORIZATION)
      .substring(TokenAuthenticationRequestFilter.PREFIX.length());

    final User user = this.tokenRepository.findUserByCompactTokenOrNull(
      compactToken
    );

    if (null == user) {
      throw new UserNotFoundException("");
    }

    return user;
  }

  @GetMapping
  public ResponseEntity<UserDto> me(
    final HttpServletRequest request
  ) throws UserNotFoundException {
    final User user = this.getUserByCompactToken(request);

    return ResponseEntity.ok(
      this.userMapper.toUserDto(user)
    );
  }

  @PutMapping("email")
  public ResponseEntity<?> updateEmail(
    final HttpServletRequest httpServletRequest,
    @RequestBody final UpdateEmailRequest request
  ) {
    return ResponseEntity.ok().build();
  }

  @PutMapping("password")
  public ResponseEntity<?> updatePassword() {
    return ResponseEntity.ok().build();
  }

  @PutMapping(
    path = "avatar",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<?> uploadAvatar(
    final HttpServletRequest request,
    @RequestParam("file") final MultipartFile multipartFile
  ) {
    final String compactToken = request.getHeader(HttpHeaders.AUTHORIZATION)
      .substring(TokenAuthenticationRequestFilter.PREFIX.length());

    final User user = this.tokenRepository.findUserByCompactTokenOrNull(compactToken);
    if (null == user) {
      return ResponseEntity.notFound().build();
    }

    this.storageService.storeUserAvatar(
      user.identifier().toString(),
      multipartFile
    );

    return ResponseEntity.ok().build();
  }

  @DeleteMapping(path = "avatar")
  public ResponseEntity<?> deleteAvatar(
    final HttpServletRequest request
  ) {
    return ResponseEntity.ok().build();
  }
}
