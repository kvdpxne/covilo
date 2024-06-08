package me.kvdpxne.covilo.domain.model.pagination;

import java.util.Arrays;
import java.util.Optional;

/**
 * An enum representing the direction for sorting data.
 *
 * @since 0.1
 */
public enum SortDirection {

  /**
   * Represents sorting in ascending order, from lowest to highest value.
   *
   * @since 0.1
   */
  ASC,

  /**
   * Represents sorting in descending order, from highest to lowest value.
   *
   * @since 0.1
   */
  DESC;

  /**
   * This method attempts to convert a {@link String} value to a corresponding
   * {@link SortDirection} enum. It performs a case-insensitive search using the
   * `equalsIgnoreCase` method.
   *
   * @param name The String value to be converted.
   * @return An {@link Optional} containing the matching {@link SortDirection}
   * if found, otherwise empty.
   * @since 0.1
   */
  public static Optional<SortDirection> from(
    final String name
  ) {
    return Arrays.stream(values())
      .filter(it -> it.name().equalsIgnoreCase(name))
      .findFirst();
  }

  /**
   * Checks if this {@link SortDirection} is ascending.
   *
   * @return true if this {@link SortDirection} is {@link #ASC}, false
   * otherwise.
   * @since 0.1
   */
  public boolean isAscending() {
    return this.equals(ASC);
  }

  /**
   * Checks if this {@link SortDirection} is descending.
   *
   * @return true if this {@link SortDirection} is {@link #DESC}, false
   * otherwise.
   * @since 0.1
   */
  public boolean isDescending() {
    return this.equals(DESC);
  }
}
