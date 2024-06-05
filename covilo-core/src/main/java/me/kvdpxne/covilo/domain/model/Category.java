package me.kvdpxne.covilo.domain.model;

import java.util.HashMap;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.domain.aggregation.Nameable;
import me.kvdpxne.covilo.infrastructure.uid.Uid;
import me.kvdpxne.covilo.shared.ApacheEqualsBuilder;
import me.kvdpxne.covilo.shared.ApacheHashCodeBuilder;

/**
 * Represents a category entity.
 */
@SuppressWarnings({"ClassCanBeRecord", "LombokGetterMayBeUsed"})
public final class Category
  implements Identifiable<String>, Nameable {

  private final String identifier;
  private final String name;
  private final Classification classification;

  /**
   * Constructs a new {@link Category} instance with the specified identifier,
   * name, and classification identifier.
   * <p>
   * It's recommended to use the {@link CategoryBuilder} to construct a
   * {@link Category} object instead of using this constructor directly.
   *
   * @param identifier               The unique identifier of the category.
   * @param name                     The name of the category.
   * @param classification The unique identifier of the
   *                                 classification.
   */
  public Category(
    final String identifier,
    final String name,
    final Classification classification
  ) {
    this.identifier = identifier;
    this.name = name;
    this.classification = classification;
  }

  /**
   * Returns a new {@link CategoryBuilder} instance to build a {@link Category}
   * object.
   *
   * @return A {@link CategoryBuilder} instance.
   */
  public static CategoryBuilder builder() {
    return new CategoryBuilder();
  }

  /**
   * Returns a new {@link CategoryBuilder} initialized with the current
   * {@link Category}'s state.
   *
   * @return A {@link CategoryBuilder} instance.
   */
  public CategoryBuilder toBuilder() {
    return new CategoryBuilder(
      this.identifier,
      this.name,
      this.classification
    );
  }

  /**
   * Returns the identifier of the category.
   *
   * @return The identifier.
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * Returns the name of the category.
   *
   * @return The name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Returns the classification identifier of the category.
   *
   * @return The classification identifier.
   */
  public Classification getClassification() {
    return this.classification;
  }

  /**
   * alias for
   */
  public String getClassificationIdentifier() {
    return this.getClassification().getIdentifier();
  }

  /**
   *
   */
  public String getClassificationName() {
    return this.getClassification().getName();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * <p>
   * Two {@link Category} objects are considered equal if they have the same
   * {@code identifier}, {@code name} and {@code classificationIdentifier}.
   *
   * @param o The reference object with which to compare.
   * @return {@code true} if this object is the same as the {@code o} argument;
   * {@code false} otherwise.
   */
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
    final var that = (Category) o;
    return new ApacheEqualsBuilder()
      .appendIgnoreCase(this.identifier, that.identifier)
      .appendIgnoreCase(this.name, that.name)
      .append(this.classification, that.classification)
      .isEquals();
  }

  /**
   * Returns a hash code value for the object. This method is supported for the
   * benefit of hash tables such as those provided by {@link HashMap}.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return new ApacheHashCodeBuilder()
      .appendIgnoreCase(this.identifier)
      .appendIgnoreCase(this.name)
      .append(this.classification)
      .toHashCode();
  }

  /**
   * Returns a string representation of the object. In general, the
   * {@code toString} method returns a string that "textually represents" this
   * object. The result should be a concise but informative representation that
   * is easy for a person to read.
   *
   * @return A string representation of the object.
   */
  @Override
  public String toString() {
    return STR."""
      Category{
        identifier="\{this.identifier}",
        name="\{this.name}",
        classificationIdentifier="\{this.classification}"
      }""";
  }

  /**
   * Builder class for constructing {@link Category} objects.
   */
  public static final class CategoryBuilder
    implements Buildable<Category> {

    private String identifier;
    private String name;
    private Classification classification;

    /**
     * Constructs a new instance of {@link CategoryBuilder} with the specified
     * identifier, name, and classification identifier.
     *
     * @param identifier               The identifier.
     * @param name                     The name.
     * @param classification The classification identifier.
     */
    private CategoryBuilder(
      final String identifier,
      final String name,
      final Classification classification
    ) {
      this.identifier = identifier;
      this.name = name;
      this.classification = classification;
    }

    /**
     * Default constructor.
     */
    private CategoryBuilder() {
      // ...
    }

    /**
     * Sets the identifier of the category.
     *
     * @param identifier The identifier.
     * @return This {@link CategoryBuilder} instance.
     */
    public CategoryBuilder withIdentifier(
      final String identifier
    ) {
      this.identifier = identifier;
      return this;
    }

    /**
     * Sets a random identifier for the category.
     *
     * @return This {@link CategoryBuilder} instance.
     */
    public CategoryBuilder withRandomIdentifier() {
      this.identifier = Uid.next();
      return this;
    }

    /**
     * Sets the name of the category.
     *
     * @param name The name.
     * @return This {@link CategoryBuilder} instance.
     */
    public CategoryBuilder withName(
      final String name
    ) {
      this.name = name;
      return this;
    }

    /**
     * Sets the classification identifier of the category.
     *
     * @param classification The classification identifier.
     * @return This {@link CategoryBuilder} instance.
     */
    public CategoryBuilder withClassification(
      final Classification classification
    ) {
      this.classification = classification;
      return this;
    }

    /**
     * Builds a new {@link Category} instance with the configured properties.
     *
     * @return A {@link Category} instance.
     */
    @Override
    public Category build() {
      return new Category(
        this.identifier,
        this.name,
        this.classification
      );
    }
  }
}