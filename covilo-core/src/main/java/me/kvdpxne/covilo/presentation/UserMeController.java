package me.kvdpxne.covilo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.UserDto;
import me.kvdpxne.covilo.application.mapper.IUserMapper;
import me.kvdpxne.covilo.application.payload.DeleteUserMeRequest;
import me.kvdpxne.covilo.application.payload.UpdateUserMeEmailRequest;
import me.kvdpxne.covilo.application.payload.UpdateUserMePasswordRequest;
import me.kvdpxne.covilo.common.constants.Endpoints;
import me.kvdpxne.covilo.domain.model.User;
import me.kvdpxne.covilo.domain.port.out.UserMeServicePort;
import me.kvdpxne.covilo.infrastructure.image.ImageConverterService;
import me.kvdpxne.covilo.infrastructure.image.ImageMimeTypeNotAvailableException;
import me.kvdpxne.covilo.infrastructure.image.ImageMimeTypeNotSupportedException;
import me.kvdpxne.covilo.infrastructure.image.ImageType;
import me.kvdpxne.covilo.infrastructure.security.UserAccountDetails;
import me.kvdpxne.covilo.infrastructure.storage.FileSystemStorageService;
import me.kvdpxne.covilo.infrastructure.storage.StorageLocationType;
import me.kvdpxne.covilo.infrastructure.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping(
  path = Endpoints.USER_ME
)
@RestController
public final class UserMeController {

  private final IUserMapper userMapper;
  private final StorageService storageService;

  private final ImageConverterService imageConverterService;

  private final UserMeServicePort userMeService;

  /**
   *
   */
  @Operation
  @GetMapping
  public UserDto getMe(
    @AuthenticationPrincipal
    final UserAccountDetails principal
  ) {
    return this.userMapper.toUserDto(
      principal.user()
    );
  }

  /**
   * Updates the email address of the currently authenticated user.
   */
  @PutMapping("email")
  public void updateUserMeEmail(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestBody
    final UpdateUserMeEmailRequest request
  ) {
    this.userMeService.updateMeEmail(
      principal.user(),
      request.newEmail(),
      request.currentPassword()
    );
  }

  /**
   * Updates the password of the currently authenticated user.
   */
  @PutMapping("password")
  public void updateUserMePassword(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestBody
    final UpdateUserMePasswordRequest request
  ) {
    this.userMeService.updateMePassword(
      principal.user(),
      request.newPassword(),
      request.confirmedPassword(),
      request.currentPassword()
    );
  }

  @PostMapping(
    path = "avatar",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public void uploadAvatar(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestParam("file")
    final MultipartFile multipartFile
  ) {
    final String mimeType = multipartFile.getContentType();
    if (null == mimeType || mimeType.isBlank()) {
      throw new ImageMimeTypeNotAvailableException();
    }

    ImageType.getImageTypeBy(
      imageType -> imageType.getMimeType().equals(mimeType)
    ).orElseThrow(() -> new ImageMimeTypeNotSupportedException(mimeType));

    if (this.storageService instanceof FileSystemStorageService storage) {
      final User me = principal.user();
      // Closing the input and output streams is unnecessary because the method
      // closes the streams when converting an image when they are no longer
      // needed for its proper execution.
      this.imageConverterService.convertImage(
        storage.openInputStream(multipartFile),
        storage.openOutputStream(
          me.getAvatarFileName(),
          StorageLocationType.AVATAR
        )
      );
      this.userMeService.updateLastModifiedDate(me);
    }
  }

  @DeleteMapping(
    path = "avatar"
  )
  public void deleteAvatar(
    @AuthenticationPrincipal
    final UserAccountDetails principal
  ) {
    this.storageService.deleteUserAvatar(
      principal.getUsername()
    );
    this.userMeService.updateLastModifiedDate(principal.user());
  }

  @DeleteMapping
  public void deleteUserMe(
    @AuthenticationPrincipal
    final UserAccountDetails principal,
    @RequestBody
    final DeleteUserMeRequest request
  ) {
    this.userMeService.deleteMe(
      principal.user(),
      request.currentPassword()
    );
  }
}
