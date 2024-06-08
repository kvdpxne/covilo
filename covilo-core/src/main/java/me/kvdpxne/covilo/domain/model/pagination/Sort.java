package me.kvdpxne.covilo.domain.model.pagination;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.kvdpxne.covilo.domain.aggregation.Buildable;

public class Sort
  implements Iterable<SortOrder>, Serializable, Comparable<SortOrder>,
  Sortable {

  private final Set<SortOrder> orders;

  protected Sort(final Set<SortOrder> orders) {
    this.orders = orders;
  }

  protected Sort() {
    this(new HashSet<>());
  }

  public static Sort by(final Stream<SortOrder> orders) {
    return new Sort(orders.collect(Collectors.toSet()));
  }

  public static Sort by(final SortOrder... orders) {
    return new Sort(Arrays.stream(orders).collect(Collectors.toSet()));
  }

  public static Sort by(final String property) {
    return new Sort(Set.of(new SortOrder(property)));
  }

  public static Sort by(final String... properties) {
    return new Sort(
      Arrays.stream(properties)
        .map(SortOrder::new)
        .collect(Collectors.toSet())
    );
  }


  @Override
  public Collection<SortOrder> getOrders() {
    return List.copyOf(this.orders);
  }

  @Override
  public boolean hasOrders() {
    return !this.orders.isEmpty();
  }

  @Override
  public int compareTo(final SortOrder o) {
    return 0;
  }

  @Override
  public Iterator<SortOrder> iterator() {
    return this.orders.iterator();
  }

  public static class SortChainBuilder
    implements Buildable<Sort> {

    private Sort sort;

    public SortChainBuilder(final Sort sort) {
      this.sort = sort;
    }

    public SortChainBuilder append(final SortOrder order) {
      if (!this.sort.orders.add(order)) {
        throw new IllegalStateException("");
      }
      return this;
    }

    @Override
    public Sort build() {
      return this.sort;
    }
  }
}
