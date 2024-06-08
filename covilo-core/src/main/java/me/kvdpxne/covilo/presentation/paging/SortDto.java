package me.kvdpxne.covilo.presentation.paging;

import me.kvdpxne.covilo.domain.model.pagination.Sort;
import me.kvdpxne.covilo.domain.model.pagination.Sortable;

public record SortDto(
  SortOrderDto[] orders
) {

  public static SortDto from(final Sortable sort) {
    if (null == sort) {
      return new SortDto(new SortOrderDto[0]);
    }

    return new SortDto(
      sort.getOrders()
        .stream()
        .map(SortOrderDto::from)
        .toArray(SortOrderDto[]::new)
    );
  }
}
