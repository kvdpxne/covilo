package me.kvdpxne.covilo.domain.model;

import java.util.HashMap;
import me.kvdpxne.covilo.domain.aggregation.Buildable;
import me.kvdpxne.covilo.domain.aggregation.Identifiable;
import me.kvdpxne.covilo.domain.aggregation.Nameable;
import me.kvdpxne.covilo.infrastructure.uid.Uid;

/**
 * Represents a classification entity.
 */
@SuppressWarnings({"ClassCanBeRecord"})
public final class Classification
  implements Identifiable<String>, Nameable {

  private final String identifier;
  private final String name;

  /**
   * Constructs a new {@link Classification} instance with the specified
   * identifier and name.
   * <p>
   * It's recommended to use the {@link ClassificationBuilder} to construct a
   * {@link Classification} object instead of using this constructor directly.
   *
   * @param identifier The unique identifier of the classification.
   * @param name       The name of the classification.
   */
  public Classification(
    final String identifier,
    final String name
  ) {
    this.identifier = identifier;
    this.name = name;
  }

  /**
   * Returns a new {@link ClassificationBuilder} instance to build a
   * {@link Classification} object.
   *
   * @return A {@link ClassificationBuilder} instance.
   */
  public static ClassificationBuilder builder() {
    return new ClassificationBuilder();
  }

  /**
   * Returns a new {@link ClassificationBuilder} initialized with the current
   * {@link Classification}'s state.
   *
   * @return A {@link ClassificationBuilder} instance.
   */
  public ClassificationBuilder toBuilder() {
    return new ClassificationBuilder(
      this.identifier,
      this.name
    );
  }

  /**
   * Returns the identifier of the classification.
   *
   * @return The identifier.
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * Returns the name of the classification.
   *
   * @return The name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * <p>
   * Two {@link Classification} objects are considered equal if they have the
   * same {@code identifier} and {@code name}.
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
    final var that = (Classification) o;
    return this.identifier.equalsIgnoreCase(that.identifier) &&
      this.name.equalsIgnoreCase(that.name);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the
   * benefit of hash tables such as those provided by {@link HashMap}.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    var result = this.identifier.hashCode();
    result = 31 * result + this.name.hashCode();
    return result;
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
      Classification{
        identifier="\{this.identifier}",
        name="\{this.name}"
      }""";
  }

  /**
   * Builder class for constructing {@link Classification} objects.
   */
  public static final class ClassificationBuilder
    implements Buildable<Classification> {

    private String identifier;
    private String name;

    /**
     * Constructs a new {@link ClassificationBuilder} with the specified
     * identifier and name.
     *
     * @param identifier The identifier of the classification.
     * @param name       The name of the classification.
     */
    private ClassificationBuilder(
      final String identifier,
      final String name
    ) {
      this.identifier = identifier;
      this.name = name;
    }

    /**
     * Default constructor.
     */
    private ClassificationBuilder() {
      // ...
    }

    /**
     * Sets the identifier of the classification.
     *
     * @param identifier The identifier.
     * @return This {@link ClassificationBuilder} instance.
     */
    public ClassificationBuilder withIdentifier(
      final String identifier
    ) {
      this.identifier = identifier;
      return this;
    }

    /**
     * Sets a random identifier for the classification.
     *
     * @return This {@link ClassificationBuilder} instance.
     */
    public ClassificationBuilder withRandomIdentifier() {
      this.identifier = Uid.next();
      return this;
    }

    /**
     * Sets the name of the classification.
     *
     * @param name The name.
     * @return This {@link ClassificationBuilder} instance.
     */
    public ClassificationBuilder withName(
      final String name
    ) {
      this.name = name;
      return this;
    }

    /**
     * Builds a new {@link Classification} instance with the configured
     * properties.
     *
     * @return A {@link Classification} instance.
     */
    @Override
    public Classification build() {
      return new Classification(
        this.identifier,
        this.name
      );
    }
  }
}
