package me.kvdpxne.covilo.domain.model.pagination;

import java.util.Collection;

public class UnlimitedPage<T>
  implements Page<T> {

  private final Collection<T> content;

  private final Sortable sortable;

  public UnlimitedPage(
    final Collection<T> content,
    final Sortable sortable
  ) {
    this.content = content;
    this.sortable = sortable;
  }

  @Override
  public Collection<T> getContent() {
    return this.content;
  }

  @Override
  public int getIndex() {
    return 0;
  }

  @Override
  public int getSize() {
    return this.content.size();
  }

  @Override
  public long getOffset() {
    return 0;
  }

  @Override
  public long getTotalPages() {
    return 1;
  }

  @Override
  public long getTotalElements() {
    return this.getSize();
  }

  @Override
  public Sortable getSortable() {
    return this.sortable;
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
    return true;
  }

  @Override
  public boolean isLast() {
    return true;
  }

  @Override
  public boolean isLimited() {
    return false;
  }

  @Override
  public boolean isUnlimited() {
    return true;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }
}
