package me.kvdpxne.covilo.domain.aggregation;

import java.time.LocalDateTime;

/**
 * Represents an auditable entity, providing information about creation and
 * modification timestamps.
 *
 * <p>
 * An auditable entity tracks the date and time when it was created and
 * last modified.
 * </p>
 */
public interface Auditable {

  /**
   * Retrieves the date and time when the entity was created.
   *
   * @return The creation timestamp.
   */
  LocalDateTime createdDate();

  /**
   * Retrieves the date and time when the entity was last modified.
   *
   * @return The last modification timestamp.
   */
  LocalDateTime lastModifiedDate();

  /**
   * Checks if the entity was created.
   *
   * @return {@code true} if the creation timestamp is not null,
   * otherwise {@code false}.
   */
  default boolean wasCreated() {
    return null != this.createdDate();
  }

  /**
   * Checks if the entity was modified.
   *
   * @return {@code true} if the last modification timestamp is not null,
   * otherwise {@code false}.
   */
  default boolean wasModified() {
    return null != this.lastModifiedDate();
  }
}
