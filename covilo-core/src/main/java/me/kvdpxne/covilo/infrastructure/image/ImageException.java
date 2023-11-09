package me.kvdpxne.covilo.infrastructure.image;

public class ImageException extends RuntimeException {

  public ImageException(final String message) {
    super(message);
  }

  public ImageException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
