package me.kvdpxne.covilo.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.ITokenService;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.application.payload.UpdateEmailRequest;
import me.kvdpxne.covilo.application.payload.UpdatePasswordRequest;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.persistence.ITokenRepository;
import me.kvdpxne.covilo.domain.service.UserLifecycleService;
import me.kvdpxne.covilo.infrastructure.image.ImageConverterService;
import me.kvdpxne.covilo.infrastructure.image.ImageMimeTypeNotAvailableException;
import me.kvdpxne.covilo.infrastructure.image.ImageMimeTypeNotSupportedException;
import me.kvdpxne.covilo.infrastructure.image.ImageType;
import me.kvdpxne.covilo.infrastructure.security.TokenAuthenticationRequestFilter;
import me.kvdpxne.covilo.infrastructure.security.UserAccountDetails;
import me.kvdpxne.covilo.infrastructure.storage.FileSystemStorageService;
import me.kvdpxne.covilo.infrastructure.storage.StorageLocationType;
import me.kvdpxne.covilo.infrastructure.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@PreAuthorize("hasRole('USER')")
@RequestMapping(path = "api/0.1.0/me")
@RestController
public final class UserMeController {

  private final ITokenRepository tokenRepository;
  private final IUserMapper userMapper;
  private final StorageService storageService;
  private final ITokenService tokenService;
  private final UserLifecycleService userLifecycleService;

  private final ImageConverterService imageConverterService;

  private User getUserByCompactToken(
    final HttpServletRequest request
  ) throws UserNotFoundException {
    final String compactToken = request
      .getHeader(HttpHeaders.AUTHORIZATION)
      .substring(TokenAuthenticationRequestFilter.PREFIX.length());

    final String email = this.tokenService.extractSubject(compactToken);
    final User user = this.userLifecycleService.getUserByEmail(email);

    if (null == user) {
      throw new UserNotFoundException("");
    }

    return user;
  }

  @GetMapping("me")
  public UserDto getMe(
    @AuthenticationPrincipal
    final UserAccountDetails principal
  ) {
    return this.userMapper.toUserDto(
      principal.user()
    );
  }

  @PutMapping("email")
  public void updateEmail(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestBody
    final UpdateEmailRequest request
  ) {
    this.userLifecycleService.updateUserEmail(
      principal.user(),
      request.newEmail()
    );
  }

  @PutMapping("password")
  public void updatePassword(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestBody
    final UpdatePasswordRequest request
  ) {
    this.userLifecycleService.updateUserPassword(
      principal.user(),
      request.newPassword()
    );
  }

  @PostMapping(
    path = "avatar",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<?> uploadAvatar(
    final HttpServletRequest request,
    @RequestParam("file") final MultipartFile multipartFile
  ) {
    final User user;
    try {
      user = this.getUserByCompactToken(request);
    } catch (final UserNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }
    final String mimeType = multipartFile.getContentType();
    if (null == mimeType || mimeType.isBlank()) {
      throw new ImageMimeTypeNotAvailableException();
    }
    ImageType.getImageTypeBy(
      imageType -> imageType.getMimeType().equals(mimeType)
    ).orElseThrow(() -> new ImageMimeTypeNotSupportedException(mimeType));
    if (this.storageService instanceof FileSystemStorageService
      fileSystemStorage
    ) {
      // Closing the input and output streams is unnecessary because the method
      // closes the streams when converting an image when they are no longer
      // needed for its proper execution.
      this.imageConverterService.convertImage(
        fileSystemStorage.openInputStream(multipartFile),
        fileSystemStorage.openOutputStream(
          user.getAvatarFileName(),
          StorageLocationType.AVATAR
        )
      );
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("avatar")
  public ResponseEntity<?> deleteAvatar(
    @AuthenticationPrincipal
    final UserAccountDetails principal
  ) {
    return ResponseEntity.ok().build();
  }
}
