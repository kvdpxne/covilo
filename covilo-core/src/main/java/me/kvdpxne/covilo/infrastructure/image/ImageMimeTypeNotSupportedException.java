package me.kvdpxne.covilo.infrastructure.image;

public final class ImageMimeTypeNotSupportedException
  extends ImageException {

  public ImageMimeTypeNotSupportedException(final String mimeType) {
    super(String.format("Mime type \"%s\" is not supported.", mimeType));
  }
}
