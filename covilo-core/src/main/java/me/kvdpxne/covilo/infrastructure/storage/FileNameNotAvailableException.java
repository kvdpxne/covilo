package me.kvdpxne.covilo.infrastructure.storage;

public final class FileNameNotAvailableException
  extends RuntimeException {

  public FileNameNotAvailableException() {
    super("The filename is not available or defined.");
  }
}
