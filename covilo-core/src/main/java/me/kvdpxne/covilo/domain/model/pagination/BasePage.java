package me.kvdpxne.covilo.domain.model.pagination;

import java.util.Collection;

/**
 * Represents a basic implementation of a paginated collection.
 *
 * @param <T> The type of items contained in the page.
 */
public class BasePage<T>
  implements Page<T> {

  /**
   * The content of the page.
   */
  private final Collection<T> content;

  /**
   * The pagination information.
   */
  private final Pageable pageable;

  /**
   * The total number of elements across all pages.
   */
  private final long totalElements;

  /**
   * Constructs a new BasePage with the provided content, pagination
   * information, and total number of elements.
   *
   * @param content       The content of the page.
   * @param pageable      The pagination information.
   * @param totalElements The total number of elements across all pages.
   */
  public BasePage(
    final Collection<T> content,
    final Pageable pageable,
    final long totalElements
  ) {
    this.content = content;
    this.pageable = pageable;

    // Calculate the total number of elements based on the provided
    // pagination information and totalElements
    this.totalElements = pageable.getIndex() + pageable.getSize() > totalElements
      ? this.getOffset() + pageable.getSize()
      : totalElements;
  }

  @Override
  public Collection<T> getContent() {
    return this.content;
  }

  @Override
  public int getIndex() {
    return this.pageable.getIndex();
  }

  @Override
  public int getSize() {
    return this.pageable.getSize();
  }

  @Override
  public long getOffset() {
    return (long) this.getIndex() * (long) this.getSize();
  }

  @Override
  public long getTotalPages() {
    return 0 < this.getSize()
      ? (long) Math.ceil((double) this.totalElements / (double) this.getSize())
      : 1;
  }

  @Override
  public long getTotalElements() {
    return this.totalElements;
  }

  @Override
  public boolean hasNext() {
    return 1 + this.getIndex() < this.getTotalPages();
  }

  @Override
  public boolean hasPrevious() {
    return 0 < this.getIndex();
  }

  @Override
  public boolean isFirst() {
    return !this.hasPrevious();
  }

  @Override
  public boolean isLast() {
    return !this.hasNext();
  }
}
