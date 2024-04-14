package me.kvdpxne.covilo.shared;

/**
 * An error thrown to indicate that an attempt was made to initialize an object
 * that should not be initialized.
 */
public final class ObjectInitializationError
  extends Error {

  /**
   * Constructs a new {@link ObjectInitializationError} with a default error
   * message. The message indicates that the object should not be initialized.
   */
  public ObjectInitializationError() {
    super("This object should not be initialized.");
  }
}
