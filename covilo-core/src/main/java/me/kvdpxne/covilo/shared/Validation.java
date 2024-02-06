package me.kvdpxne.covilo.shared;

import java.util.Collection;

public final class Validation {

  public static <T> T check(final T value, final String message) {
    if (null == value) {
      throw new NullPointerException();
    }
    return value;
  }

  public static <T> T check(final T value) {
    return Validation.check(value, "The given parameter cannot be null.");
  }

  /**
   * Checks a condition and throws an IllegalArgumentException if the condition
   * is true.
   *
   * @param condition The condition to check.
   * @param message   The message to include in the exception if the condition
   *                  is true.
   */
  public static void check(
    final boolean condition,
    final String message
  ) {
    if (condition) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Checks if a number is negative and throws an IllegalArgumentException if
   * it is.
   *
   * @param number  The number to check.
   * @param message The message to include in the exception if the number is
   *                negative.
   * @param <T>     The type of the number (extends Number).
   */
  public static <T extends Number> void negative(
    final T number,
    final String message
  ) {
    Validation.check(
      0d > number.doubleValue(),
      message
    );
  }

  /**
   * Checks if a number is negative and throws an IllegalArgumentException if
   * it is, using a default error message.
   *
   * @param number The number to check.
   * @param <T>    The type of the number (extends Number).
   */
  public static <T extends Number> void negative(
    final T number
  ) {
    Validation.negative(
      number,
      "The given number cannot be negative"
    );
  }

  /**
   * Checks whether the given sequence of characters is empty.
   *
   * @throws NullPointerException     If the given sequence of characters is
   *                                  null.
   * @throws IllegalArgumentException If the given character sequence is empty
   *                                  or consists only of whitespace.
   */
  public static void empty(final String sequence) {
    Validation.check(sequence);

    final int length = sequence.length();
    if (0 == length) {
      throw new IllegalArgumentException(
        "The given character sequence cannot be empty."
      );
    }

    int count = 0;
    for (int i = 0; i < length; i++) {
      if (Character.isWhitespace(sequence.charAt(i))) {
        count++;
      }
    }

    if (length == count) {
      throw new IllegalArgumentException(
        "The given character sequence cannot consist only of whitespace."
      );
    }
  }

  public static void empty(final Collection<?> collection) {
    Validation.check(collection);

    if (collection.isEmpty()) {
      throw new IllegalArgumentException("");
    }
  }

  /**
   *
   */
  private Validation() {
    throw new ObjectInitializationError();
  }
}
