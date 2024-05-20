package me.kvdpxne.pagination;

import java.util.Collection;

/**
 * Represents a page of data in a paginated collection.
 * @param <T> The type of items contained in the page.
 */
public interface Page<T> {

  /**
   * Retrieves the content of the current page.
   * @return The collection of items in the current page.
   */
  Collection<T> getContent();

  /**
   * Retrieves the index (0-based) of the current page.
   * @return The index of the current page.
   */
  int getIndex();

  /**
   * Retrieves the size of the current page.
   * @return The size of the current page.
   */
  int getSize();

  /**
   * Retrieves the offset of the current page.
   * @return The offset of the current page.
   */
  long getOffset();

  /**
   * Retrieves the total number of pages.
   * @return The total number of pages.
   */
  long getTotalPages();

  /**
   * Retrieves the total number of elements across all pages.
   * @return The total number of elements.
   */
  long getTotalElements();

  /**
   * Checks if there is a next page available.
   * @return True if there is a next page, otherwise false.
   */
  boolean hasNext();

  /**
   * Checks if there is a previous page available.
   * @return True if there is a previous page, otherwise false.
   */
  boolean hasPrevious();

  /**
   * Checks if the current page is the first page.
   * @return True if the current page is the first page, otherwise false.
   */
  boolean isFirst();

  /**
   * Checks if the current page is the last page.
   * @return True if the current page is the last page, otherwise false.
   */
  boolean isLast();
}
