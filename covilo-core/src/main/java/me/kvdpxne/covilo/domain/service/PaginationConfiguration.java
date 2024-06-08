package me.kvdpxne.covilo.domain.service;

import me.kvdpxne.covilo.domain.model.pagination.Pageable;

/**
 * Interface for pagination configuration.
 */
public interface PaginationConfiguration {

  /**
   * Retrieves the default page size.
   *
   * @return The default page size.
   */
  int getDefaultPageSize();

  /**
   * Retrieves the maximum page size allowed.
   *
   * @return The maximum page size.
   */
  int getMaximumPageSize();

  /**
   *
   */
  boolean isUnlimitedAllowed();

  /**
   * Retrieves the default pageable configuration.
   *
   * @return The default pageable configuration.
   */
  Pageable getDefaultPageable();
}
