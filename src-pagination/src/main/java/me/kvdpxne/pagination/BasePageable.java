package me.kvdpxne.pagination;

/**
 * Represents a basic implementation of pagination information for querying a
 * specific page of data.
 */
public class BasePageable
  implements Pageable {

  /**
   * The index (0-based) of the requested page.
   */
  private final int index;

  /**
   * The size of the page.
   */
  private final int size;

  /**
   * Constructs a new BasePageable with the provided index and size.
   *
   * @param index The index (0-based) of the requested page.
   * @param size The size of the page.
   */
  public BasePageable(
    final int index,
    final int size
  ) {
    this.index = index;
    this.size = size;
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public long getOffset() {
    return (long) this.index * (long) this.size;
  }
}
