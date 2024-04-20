package me.kvdpxne.covilo.domain.aggregation;

import java.util.Locale;

/**
 * Represents an entity that has a name. Implementations of this interface
 * provide a method to retrieve the name of the entity.
 */
public interface Nameable {

  /**
   * Retrieves the name of the entity.
   *
   * @return The name of the entity.
   */
  String getName();

  /**
   * Retrieves the name of the entity in lowercase using the specified locale.
   *
   * @param locale The locale to use for converting the name to lowercase.
   * @return The lowercase name of the entity.
   * @throws NullPointerException If the entity name is {@code null}.
   */
  default String getLowerName(final Locale locale) {
    return this.getName().toLowerCase(locale);
  }

  /**
   * Retrieves the name of the entity in lowercase using the default locale
   * (English).
   *
   * @return The lowercase name of the entity.
   * @throws NullPointerException If the entity name is {@code null}.
   */
  default String getLowerName() {
    return this.getLowerName(Locale.ENGLISH);
  }
}
