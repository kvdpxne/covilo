package me.kvdpxne.covilo.domain.model.pagination;

import java.util.Optional;

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

  private final Sortable sortable;

  /**
   * Constructs a new BasePageable with the provided index and size.
   *
   * @param index The index (0-based) of the requested page.
   * @param size The size of the page.
   */
  public BasePageable(
    final int index,
    final int size,
    final Sortable sortable
  ) {
    this.index = index;
    this.size = size;
    this.sortable = sortable;
  }

  public BasePageable(
    final Pageable pageable
  ) {
    this(pageable.getIndex(), pageable.getSize(), pageable.getSortable());
  }

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
    this(index, size, null);
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

  @Override
  public Sortable getSortable() {
    return this.sortable;
  }

  /**
   *
   */
  public Optional<Sortable> getSortableAs() {
    return Optional.ofNullable(this.getSortable());
  }
}
