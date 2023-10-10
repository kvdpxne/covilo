package me.kvdpxne.covilo.infrastructure.image;

public final class ImageMimeTypeNotSupportedException
  extends RuntimeException {

  public ImageMimeTypeNotSupportedException(final String message) {
    super(message);
  }
}
