package me.kvdpxne.covilo.presentation.paging;

import java.util.function.Function;
import me.kvdpxne.covilo.domain.model.pagination.Page;

/**
 * Represents a Data Transfer Object (DTO) for paginated data.
 *
 * @param <T>           The type of items contained in the page.
 * @param content       The array of items in the current page.
 * @param index         The index (0-based) of the current page.
 * @param size          The size of the current page.
 * @param totalPages    The total number of pages.
 * @param totalElements The total number of elements across all pages.
 * @param hasNext       Indicates whether there is a next page available.
 * @param hasPrevious   Indicates whether there is a previous page available.
 * @param isFirst       Indicates whether the current page is the first page.
 * @param isLast        Indicates whether the current page is the last page.
 */
public record PageDto<T>(
  T[] content,
  int index,
  int size,
  long totalPages,
  long totalElements,
  boolean hasNext,
  boolean hasPrevious,
  boolean isFirst,
  boolean isLast
) {

  @SuppressWarnings("unchecked")
  public static <T, R> PageDto<R> of(
    final Page<T> page,
    final Function<T, R> function
    ) {
    return (PageDto<R>) new PageDto<>(
      page.getContent().stream().map(function).toArray(),
      page.getIndex(),
      page.getSize(),
      page.getTotalPages(),
      page.getTotalElements(),
      page.hasNext(),
      page.hasPrevious(),
      page.isFirst(),
      page.isLast()
    );
  }
}