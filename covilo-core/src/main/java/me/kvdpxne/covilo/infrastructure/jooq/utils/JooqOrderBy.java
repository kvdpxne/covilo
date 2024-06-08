package me.kvdpxne.covilo.infrastructure.jooq.utils;

import java.util.List;
import java.util.Map;
import me.kvdpxne.covilo.domain.model.pagination.SortOrder;
import me.kvdpxne.covilo.domain.model.pagination.Sortable;
import org.jooq.Field;
import org.jooq.SortField;

/**
 * Utility class for creating JOOQ SortField objects based on SortOrder
 * specifications.
 */
public final class JooqOrderBy {

  /**
   *
   */
  public static SortField<?> orderBy(
    final SortOrder order,
    final Field<?> field
  ) {
    SortField<?> sortField;

    // TODO case sensitive
    //order.isCaseSensitive()
    //field.collate("NOCASE");

    // Apply a sorting direction (ascending or descending)
    if (order.isAscending()) {
      sortField = field.asc();
    } else {
      sortField = field.desc();
    }

    // Apply null handling strategy (nulls first or last)
    if (order.nullsFirst()) {
      sortField = sortField.nullsFirst();
    } else {
      sortField = sortField.nullsLast();
    }

    return sortField;
  }

  /**
   *
   */
  public static List<? extends SortField<?>> orderBy(
    final Sortable sortable,
    final Map<String, Field<?>> fields
  ) {
    if (null == sortable || null == fields || !sortable.hasOrders()
      || fields.isEmpty()) {
      return List.of();
    }

    return sortable.getOrders()
      .stream()
      .filter(it -> fields.containsKey(it.getProperty()))
      .map(it -> JooqOrderBy.orderBy(it, fields.get(it.getProperty())))
      .toList();
  }
}
