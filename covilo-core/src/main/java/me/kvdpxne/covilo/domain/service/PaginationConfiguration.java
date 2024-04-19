package me.kvdpxne.covilo.domain.service;

import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Interface for pagination configuration.
 */
public interface PaginationConfiguration {

  /**
   * Retrieves the default pageable configuration.
   *
   * @return The default pageable configuration.
   */
  Pageable getDefaultPageable();

  /**
   * Retrieves the default pageable configuration, or the provided pageable
   * if not {@code null}.
   *
   * @param pageable The pageable configuration to use if not {@code null}.
   * @return The default pageable configuration if pageable is {@code null},
   * otherwise the provided pageable.
   */
  default Pageable getDefaultPageableOr(
    final Pageable pageable
  ) {
    return null != pageable ? pageable : this.getDefaultPageable();
  }
}
