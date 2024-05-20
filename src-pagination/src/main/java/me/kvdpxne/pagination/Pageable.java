package me.kvdpxne.pagination;

/**
 * Represents pagination information for querying a specific page of data.
 */
public interface Pageable {

  /**
   * Retrieves the index (0-based) of the requested page.
   * @return The index of the requested page.
   */
  int getIndex();

  /**
   * Retrieves the size of the page.
   * @return The size of the page.
   */
  int getSize();

  /**
   * Retrieves the offset of the requested page.
   * @return The offset of the requested page.
   */
  long getOffset();
}
