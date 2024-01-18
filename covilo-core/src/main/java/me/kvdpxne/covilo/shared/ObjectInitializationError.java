package me.kvdpxne.covilo.shared;

public final class ObjectInitializationError
  extends Error {

  public ObjectInitializationError() {
    super("This object should not be initialized.");
  }
}
