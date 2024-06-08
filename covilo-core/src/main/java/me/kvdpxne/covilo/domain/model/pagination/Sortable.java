package me.kvdpxne.covilo.domain.model.pagination;

import java.util.Collection;

/**
 *
 */
public interface Sortable {

  /**
   *
   */
  Collection<SortOrder> getOrders();

  /**
   *
   */
  boolean hasOrders();
}
