package me.kvdpxne.covilo.infrastructure.image;

public final class ImageMimeTypeNotAvailableException
  extends ImageException {

  public ImageMimeTypeNotAvailableException() {
    super("The mime type is not available or defined.");
  }
}
