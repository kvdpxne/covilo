package me.kvdpxne.covilo.domain.model;

/**
 * Represents the gender of an individual.
 */
public enum Gender {

  /**
   * Male gender.
   */
  MALE,

  /**
   * Female gender.
   */
  FEMALE,

  /**
   * No gender specified or undefined.
   */
  NONE;

  /**
   * Converts a {@link Boolean} value to a corresponding {@link Gender} enum
   * value. If the input is {@code null}, the method returns
   * {@link Gender#NONE}, indicating no gender specified. If the input is
   * {@code true}, it returns {@link Gender#MALE}; otherwise, it returns
   * {@link Gender#FEMALE}.
   *
   * @param isMale A {@link Boolean} value indicating if the gender is male.
   *               {@code true} represents male, {@code false} represents
   *               female, and {@code null} represents no gender specified
   *               ({@link Gender#NONE}).
   * @return The {@link Gender} enum value corresponding to the {@link Boolean}
   * input.
   */
  public static Gender of(
    final Boolean isMale
  ) {
    // If the input is null, return NONE
    if (null == isMale) {
      return NONE;
    }
    // If the input is true, return MALE; otherwise, return FEMALE
    return isMale ? MALE : FEMALE;
  }
}
