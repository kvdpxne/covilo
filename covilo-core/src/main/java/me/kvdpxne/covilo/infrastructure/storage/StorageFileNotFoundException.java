package me.kvdpxne.covilo.infrastructure.storage;

public class StorageFileNotFoundException extends StorageException {

  public StorageFileNotFoundException() {
  }

  public StorageFileNotFoundException(final String message) {
    super(message);
  }

  public StorageFileNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
