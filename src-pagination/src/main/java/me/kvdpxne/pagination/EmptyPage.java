package me.kvdpxne.pagination;

import java.util.Collection;
import java.util.List;

/**
 * Represents an empty page in pagination, containing no elements.
 * <p>
 * This class implements the {@link Page} interface and represents a page with
 * no content. It is typically used when there are no elements to display for a
 * given page index and size.
 *
 * @param <T> The type of elements in the page.
 */
public final class EmptyPage<T>
  implements Page<T> {

  /**
   * Constructs a new {@link EmptyPage} instance.
   */
  public EmptyPage() {
    // ...
  }

  /**
   * Returns an empty list as there are no elements in the empty page.
   *
   * @return An empty list.
   */
  @Override
  public Collection<T> getContent() {
    return List.of();
  }

  @Override
  public int getIndex() {
    return 0;
  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public long getOffset() {
    return 0;
  }

  @Override
  public long getTotalPages() {
    return 0;
  }

  @Override
  public long getTotalElements() {
    return 0;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public boolean isFirst() {
    return false;
  }

  @Override
  public boolean isLast() {
    return false;
  }
}
