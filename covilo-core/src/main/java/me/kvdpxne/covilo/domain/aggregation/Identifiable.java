package me.kvdpxne.covilo.domain.aggregation;

import java.io.Serializable;

/**
 * An interface for objects that can be identified by a unique identifier.
 * The identifier is expected to be of a serializable type.
 *
 * @param <T> The type of the identifier, which must be serializable.
 */
public interface Identifiable<T extends Serializable> {

  /**
   * Retrieves the unique identifier of the object.
   *
   * @return The identifier of the object.
   */
  T getIdentifier();

  /**
   * Checks if the object is new, i.e., if it has not been assigned an
   * identifier yet.
   *
   * @return {@code true} if the object is new, otherwise {@code false}.
   */
  default boolean isNew() {
    return null == this.getIdentifier();
  }
}
