package me.kvdpxne.covilo.presentation.paging;

import java.util.Arrays;
import me.kvdpxne.covilo.domain.model.pagination.Sort;

public record SortRequest(
  SortOrderRequest[] orders
) {

  public Sort toSort() {
    return Sort.by(
      Arrays.stream(this.orders).map(SortOrderRequest::toSortOrder)
    );
  }
}
