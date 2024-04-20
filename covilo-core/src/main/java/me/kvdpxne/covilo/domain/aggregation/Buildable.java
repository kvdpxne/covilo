package me.kvdpxne.covilo.domain.aggregation;

/**
 * Represents an entity that can be built.
 *
 * @param <T> The type of object that can be built.
 */
public interface Buildable<T> {

  /**
   * Builds an instance of the specified type.
   *
   * @return An instance of the specified type.
   */
  T build();
}
