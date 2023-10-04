package me.kvdpxne.covilo.infrastructure.storage;

public class StorageException extends RuntimeException {

  public StorageException() {
  }

  public StorageException(final String message) {
    super(message);
  }

  public StorageException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
