package me.kvdpxne.covilo.domain.persistence.paging;

import me.kvdpxne.covilo.shared.Validation;

/**
 * Represents a range of pages in pagination, defined by a page number and size.
 *
 * <p>
 * Note: Direct instantiation using the constructor is not recommended. Use the
 * {@link #of(int, int)} method to create instances of PageRange, as it
 * provides validation for the input parameters.
 * </p>
 *
 * @param page The page number.
 * @param size The number of items per page.
 */
public record PageRange(
  int page,
  int size
) {

  /**
   * Creates a new PageRange instance with the specified page number and size.
   *
   * @param page The page number.
   * @param size The number of items per page.
   * @return A new PageRange instance.
   * @throws IllegalArgumentException If the page number is negative or the
   *                                  size is less than one.
   */
  public static PageRange of(final int page, final int size) {
    Validation.negative(page, "The page number cannot be less than zero.");
    Validation.check(1 > size, "The number of items per page cannot be less than one.");
    return new PageRange(page, size);
  }

  /**
   * Creates a new PageRange instance representing the entire range of pages.
   *
   * @return A new PageRange instance representing the entire range of pages.
   */
  public static PageRange of() {
    return new PageRange(0, Integer.MAX_VALUE);
  }
}
