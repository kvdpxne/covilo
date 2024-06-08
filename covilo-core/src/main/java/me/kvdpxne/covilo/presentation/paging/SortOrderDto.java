package me.kvdpxne.covilo.presentation.paging;

import me.kvdpxne.covilo.domain.model.pagination.SortOrder;

public record SortOrderDto(
  String property,
  boolean caseSensitive,
  String direction
) {

  public static SortOrderDto from(
    final SortOrder order
  ) {
    return new SortOrderDto(
      order.getProperty(),
      order.isCaseSensitive(),
      order.getDirection().toString()
    );
  }
}
