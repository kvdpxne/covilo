package me.kvdpxne.covilo.domain.model.pagination;

import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.shared.ApacheEqualsBuilder;
import me.kvdpxne.covilo.shared.ApacheHashCodeBuilder;
import static me.kvdpxne.covilo.domain.model.pagination.SortDirection.ASC;
import static me.kvdpxne.covilo.domain.model.pagination.SortDirection.DESC;
import static me.kvdpxne.covilo.domain.model.pagination.SortNullHandling.NULLS_FIRST;
import static me.kvdpxne.covilo.domain.model.pagination.SortNullHandling.NULLS_LAST;

public class SortOrder {

  private final String property;
  private final boolean caseSensitive;
  private final SortDirection direction;
  private final SortNullHandling nullHandling;

  /**
   * Creates a new `SortOrder` object with the specified property, case
   * sensitivity, direction, and null handling behavior.
   *
   * @param property      The name of the property to sort by.
   * @param caseSensitive Whether the sorting should be case-sensitive (default:
   *                      false).
   * @param direction     The direction of the sort (ascending or descending).
   * @param nullHandling  How null values should be handled during sorting.
   */
  public SortOrder(
    final String property,
    final boolean caseSensitive,
    final SortDirection direction,
    final SortNullHandling nullHandling
  ) {
    this.direction = direction;
    this.caseSensitive = caseSensitive;
    this.nullHandling = nullHandling;
    this.property = property;
  }

  /**
   * Creates a new `SortOrder` object with the specified property, direction,
   * and default null handling behavior (NULLS_FIRST). Case sensitivity defaults
   * to false.
   *
   * @param property  The name of the property to sort by.
   * @param direction The direction of the sort (ascending or descending).
   */
  public SortOrder(
    final String property,
    final SortDirection direction,
    final SortNullHandling nullHandling
  ) {
    this(property, false, direction, nullHandling);
  }

  /**
   * Creates a new `SortOrder` object with the specified property and default
   * direction (ascending). Case sensitivity defaults to false, and null
   * handling defaults to NULLS_FIRST.
   *
   * @param property The name of the property to sort by.
   */
  public SortOrder(
    final String property,
    final SortDirection direction
  ) {
    this(property, false, direction, NULLS_FIRST);
  }

  /**
   * Creates a new `SortOrder` object with the specified property and default
   * direction (ascending). Case sensitivity defaults to false, and null
   * handling defaults to NULLS_FIRST.
   *
   * @param property The name of the property to sort by.
   */
  public SortOrder(
    final String property
  ) {
    this(property, ASC);
  }

  /**
   * Protected constructor for internal use, likely to set default values.
   * <p>
   * This constructor is protected and intended for internal use within the
   * class or its subclasses. It's likely used to set default values for the
   * properties.
   * </p>
   */
  private SortOrder() {
    this(null);
  }

  /**
   * Creates a new builder instance for creating a `SortOrder` object.
   * <p>
   * This static method provides a fluent interface for building `SortOrder`
   * objects. It allows you to easily configure the sorting properties
   * (property, case sensitivity, direction, null handling) and build an
   * immutable `SortOrder` object.
   * </p>
   *
   * @return A new `SortOrderBuilder` instance.
   */
  public static SortOrderBuilder builder() {
    return new SortOrderBuilder();
  }

  /**
   * Creates a new `SortOrderBuilder` instance pre-populated with the current
   * object's properties.
   * <p>
   * This method allows you to create a new builder instance that is initialized
   * with the current object's properties. This is useful for modifying an
   * existing `SortOrder` object by using the builder pattern.
   * </p>
   *
   * @return A new `SortOrderBuilder` instance populated with this object's
   * properties.
   */
  public SortOrderBuilder toBuilder() {
    return new SortOrderBuilder(
      this.property,
      this.caseSensitive,
      this.direction,
      this.nullHandling
    );
  }

  /**
   * Creates a new `SortOrder` object with the specified property, keeping other
   * properties from the current instance.
   *
   * @param property The new property to sort by.
   * @return A new `SortOrder` object with the updated property.
   */
  public SortOrder withProperty(
    final String property
  ) {
    return new SortOrder(
      property,
      this.caseSensitive,
      this.direction,
      this.nullHandling
    );
  }

  /**
   * Creates a new `SortOrder` object with the specified case sensitivity,
   * keeping other properties from the current instance.
   *
   * @param caseSensitive Whether the sorting should be case-sensitive.
   * @return A new `SortOrder` object with the updated case sensitivity.
   */
  public SortOrder withCaseSensitive(
    final boolean caseSensitive
  ) {
    return new SortOrder(
      this.property,
      caseSensitive,
      this.direction,
      this.nullHandling
    );
  }

  /**
   * Creates a new `SortOrder` object with the specified direction, keeping
   * other properties from the current instance.
   *
   * @param direction The new direction for sorting.
   * @return A new `SortOrder` object with the updated direction.
   */
  public SortOrder withDirection(
    final SortDirection direction
  ) {
    return new SortOrder(
      this.property,
      this.caseSensitive,
      direction,
      this.nullHandling
    );
  }

  /**
   * Creates a new `SortOrder` object with the specified null handling strategy,
   * copying other properties from this instance.
   *
   * @param nullHandling The new null handling strategy.
   * @return A new `SortOrder` object with the updated null handling strategy.
   */
  public SortOrder withNullHandling(
    final SortNullHandling nullHandling
  ) {
    return new SortOrder(
      this.property,
      this.caseSensitive,
      this.direction,
      nullHandling
    );
  }

  /**
   * Retrieves the property name used for sorting.
   *
   * @return The name of the property to sort by.
   */
  public String getProperty() {
    return this.property;
  }

  /**
   * Checks if the sorting is case-sensitive.
   *
   * @return True if the sorting is case-sensitive, false otherwise.
   */
  public boolean isCaseSensitive() {
    return this.caseSensitive;
  }

  /**
   * Checks if the sorting is case-insensitive.
   * <p>
   * This method provides an alternative way to check for case-insensitive
   * sorting by negating the result of {@link #isCaseSensitive()}.
   * </p>
   *
   * @return True if the sorting is case-insensitive, false otherwise.
   */
  public boolean isCaseInsensitive() {
    return !this.isCaseSensitive();
  }

  /**
   * Retrieves the sorting direction.
   *
   * @return The sorting direction (ascending or descending).
   */
  public SortDirection getDirection() {
    return this.direction;
  }

  /**
   * Checks if the sorting direction is ascending.
   * <p>
   * This is a convenience method that delegates to {@link #getDirection()}
   * .isAscending().
   * </p>
   *
   * @return True if the sorting direction is ascending, false otherwise.
   */
  public boolean isAscending() {
    return ASC == this.direction;
  }

  /**
   * Checks if the sorting direction is descending.
   * This is a convenience method that delegates to
   * {@link #getDirection()#isDescending()}
   *
   * @return True if the sorting direction is descending, false otherwise.
   */
  public boolean isDescending() {
    return DESC == this.direction;
  }

  /**
   * Retrieves the strategy for handling null values during sorting.
   *
   * @return The null handling strategy.
   */
  public SortNullHandling getNullHandling() {
    return this.nullHandling;
  }

  public boolean nullsFirst() {
    return NULLS_FIRST == this.nullHandling;
  }

  public boolean nullsLast() {
    return NULLS_LAST == this.nullHandling;
  }

  @Override
  public boolean equals(
    final Object o
  ) {
    if (this == o) {
      return true;
    }

    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }

    final var that = (SortOrder) o;
    return new ApacheEqualsBuilder()
      .appendIgnoreCase(this.property, that.property)
      .append(this.caseSensitive, that.caseSensitive)
      .append(this.direction, that.direction)
      .append(this.nullHandling, that.nullHandling)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new ApacheHashCodeBuilder()
      .appendIgnoreCase(this.property)
      .append(this.caseSensitive)
      .append(this.direction)
      .append(this.nullHandling)
      .toHashCode();
  }

  /**
   * Builder class for creating immutable `SortOrder` objects.
   * <p>
   * This builder class provides a fluent interface for constructing `SortOrder`
   * objects. It allows you to easily configure the sorting properties
   * (property, case sensitivity, direction, null handling) and build an
   * immutable `SortOrder` object.
   * </p>
   */
  public static class SortOrderBuilder
    implements Buildable<SortOrder> {

    protected String property;
    protected boolean caseSensitive;
    protected SortDirection direction;
    protected SortNullHandling nullHandling;

    /**
     * Creates a new `SortOrderBuilder` instance pre-populated with the provided properties.
     *
     * This constructor allows you to create a builder instance that is pre-populated with the
     * given values for sorting properties. This is useful when you want to modify an existing
     * `SortOrder` object using the builder pattern.
     *
     * @param property The name of the property to sort by.
     * @param caseSensitive Whether the sorting should be case-sensitive.
     * @param direction The sorting direction (ascending or descending).
     * @param nullHandling How null values should be handled during sorting.
     */
    protected SortOrderBuilder(
      final String property,
      final boolean caseSensitive,
      final SortDirection direction,
      final SortNullHandling nullHandling
    ) {
      this.property = property;
      this.caseSensitive = caseSensitive;
      this.direction = direction;
      this.nullHandling = nullHandling;
    }

    /**
     * Creates a new `SortOrderBuilder` instance.
     *
     * This constructor allows you to start building a new `SortOrder` object from scratch.
     */
    protected SortOrderBuilder() {
      // ...
    }

    /**
     * Sets the property name to be used for sorting.
     *
     * This method allows you to specify the property name that the sorting should be based on.
     *
     * @param property The name of the property to sort by.
     * @return This builder instance for method chaining.
     */
    public SortOrderBuilder setProperty(
      final String property
    ) {
      this.property = property;
      return this;
    }

    /**
     *
     */
    public SortOrderBuilder setCaseSensitive(
      final boolean caseSensitive
    ) {
      this.caseSensitive = caseSensitive;
      return this;
    }

    /**
     *
     */
    public SortOrderBuilder setCaseSensitive() {
      return this.setCaseSensitive(true);
    }

    /**
     *
     */
    public SortOrderBuilder setCaseInsensitive() {
      return this.setCaseSensitive(false);
    }

    /**
     *
     */
    public SortOrderBuilder setDirection(
      final SortDirection direction
    ) {
      this.direction = direction;
      return this;
    }

    /**
     *
     */
    public SortOrderBuilder setNullHandling(
      final SortNullHandling nullHandling
    ) {
      this.nullHandling = nullHandling;
      return this;
    }

    /**
     * Builds and returns an immutable `SortOrder` object.
     *
     * This method validates the configured properties and builds an immutable `SortOrder` object.
     * It throws an `IllegalStateException` if the `property` is null or empty.
     *
     * @return A new immutable `SortOrder` object.
     * @throws IllegalStateException if the `property` is null or empty.
     */
    @Override
    public SortOrder build() {
      if (null == this.property || this.property.isBlank()) {
        throw new IllegalStateException(
          "Property cannot be null or empty"
        );
      }

      if (null == this.direction) {
        this.direction = ASC;
      }

      if (null == this.nullHandling) {
        this.nullHandling = NULLS_FIRST;
      }

      return new SortOrder(
        this.property,
        this.caseSensitive,
        this.direction,
        this.nullHandling
      );
    }
  }
}
