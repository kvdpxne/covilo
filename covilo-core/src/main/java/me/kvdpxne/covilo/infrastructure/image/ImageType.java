package me.kvdpxne.covilo.infrastructure.image;

import java.util.Collection;
import java.util.Set;
import lombok.Getter;
import me.kvdpxne.covilo.shared.Validation;

/**
 *
 */
@Getter
public enum ImageType {

  // Bitmap file.
  BMP(false, ImageMimeTypes.BMP, ".bmp"),

  // Joint Photographic Expert Group image.
  JPEG(true, ImageMimeTypes.JPEG, ".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp"),

  // Portable Network Graphics.
  PNG(false, ImageMimeTypes.PNG, ".png"),

  // Web Picture format.
  WEBP(true, ImageMimeTypes.WEBP, ".webp");

  private final boolean supported;
  private final String mimeType;
  private final Collection<String> extensions;

  /**
   * @param supported  Determines whether the image file format is supported
   *                   and does not require conversion.
   * @param mimeType   indicates the nature and format of a document, file, or
   *                   assortment of bytes.
   * @param extensions All possible extensions of the image file for which the
   *                   format applies.
   * @throws NullPointerException     If the given extension array is null or
   *                                  any element of this array is null.
   * @throws IllegalArgumentException If the given extension array has an
   *                                  element that is repeated more than once,
   *                                  it has duplicates.
   */
  ImageType(
    final boolean supported,
    final String mimeType,
    final String... extensions
  ) {
    this.supported = supported;
    this.mimeType = mimeType;

    // Packs the given extensions into an unmodifiable set, avoiding
    // duplicates, to protect against future changes to the application.
    this.extensions = Set.of(extensions);
  }

  /**
   * @throws NullPointerException     If the given extension is null.
   * @throws IllegalArgumentException If the given extension is empty or
   *                                  contains only spaces.
   */
  public static ImageType getImageTypeByExtension(
    final String extension
  ) {
    Validation.check(
      extension,
      "The given extension cannot be null."
    );
    Validation.check(
      extension.isBlank(),
      "The given extension cannot be empty or contain only spaces."
    );
    for (final ImageType imageType : ImageType.values()) {
      for (final String imageExtension : imageType.extensions) {
        if (imageExtension.equalsIgnoreCase(extension)) {
          return imageType;
        }
      }
    }
    return null;
  }
}
