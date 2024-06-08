package me.kvdpxne.covilo.presentation.paging;

import me.kvdpxne.covilo.domain.model.pagination.SortDirection;
import me.kvdpxne.covilo.domain.model.pagination.SortOrder;

public record SortOrderRequest(
  String property,
  boolean caseSensitive,
  String direction
) {

  public SortOrder toSortOrder() {
    return SortOrder.builder()
      .setProperty(this.property)
      .setCaseSensitive(this.caseSensitive)
      // TODO SortDirection default
      .setDirection(SortDirection.from(this.direction).orElse(SortDirection.ASC))
      .build();
  }
}
