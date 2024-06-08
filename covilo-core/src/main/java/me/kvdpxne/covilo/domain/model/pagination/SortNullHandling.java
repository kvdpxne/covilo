package me.kvdpxne.covilo.domain.model.pagination;

/**
 * An enum defining how null values should be handled during sorting.
 */
public enum SortNullHandling {

  /**
   * Positions rows with null values before non-null values during sorting.
   */
  NULLS_FIRST,

  /**
   * Positions rows with null values after non-null values during sorting.
   */
  NULLS_LAST
}
