package me.kvdpxne.covilo.domain.aggregation;

import java.time.LocalDateTime;

public interface Auditable {

  /**
   * Local time of creation of a model.
   */
  LocalDateTime createdDate();

  /**
   * Local creation time of the last modification of some model or null if the
   * model has never been modified.
   */
  LocalDateTime lastModifiedDate();

  /**
   *
   */
  default boolean wasModified() {
    return null != this.lastModifiedDate();
  }
}
