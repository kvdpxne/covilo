package me.kvdpxne.covilo.presentation.paging;

import me.kvdpxne.covilo.domain.model.pagination.BasePageable;
import me.kvdpxne.covilo.domain.model.pagination.Pageable;
import me.kvdpxne.covilo.domain.model.pagination.Sort;
import me.kvdpxne.covilo.domain.model.pagination.SortOrder;

/**
 * Represents a request for a specific page of data.
 * This record encapsulates the index (0-based) and size of the requested page.
 *
 * @param index The index (0-based) of the requested page.
 * @param size  The size of the requested page.
 */
public record PageRequest(
  int index,
  int size,
  SortRequest sort
) {

  public Pageable getPage() {
    return new BasePageable(
      this.index,
      this.size,
      null != this.sort ? this.sort.toSort() : null
    );
  }

  public Pageable getPage(
    final String property
    ) {
    return new BasePageable(
      this.index,
      this.size,
      Sort.by(property)
    );
  }
}