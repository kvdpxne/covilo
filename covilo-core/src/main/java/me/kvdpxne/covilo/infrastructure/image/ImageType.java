package me.kvdpxne.covilo.infrastructure.image;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import lombok.Getter;

/**
 *
 */
@Getter
public enum ImageType {

  // Bitmap file.
  BMP(ImageMimeTypes.BMP, ".bmp"),

  // Joint Photographic Expert Group image.
  JPEG(ImageMimeTypes.JPEG, ".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp"),

  // Portable Network Graphics.
  PNG(ImageMimeTypes.PNG, ".png"),

  // Web Picture format.
  WEBP(ImageMimeTypes.WEBP, ".webp");

  private final String mimeType;
  private final Collection<String> extensions;

  /**
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
    final String mimeType,
    final String... extensions
  ) {
    this.mimeType = mimeType;
    // Packs the given extensions into an unmodifiable set, avoiding
    // duplicates, to protect against future changes to the application.
    this.extensions = Set.of(extensions);
  }

  /**
   * @throws NullPointerException If the given predicate is null.
   */
  public static Optional<ImageType> getImageTypeBy(
    final Predicate<ImageType> predicate
  ) {
    Objects.requireNonNull(predicate, "The given predicate cannot be null.");
    return Arrays.stream(ImageType.values())
      .filter(predicate)
      .findFirst();
  }
}
