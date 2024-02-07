package me.kvdpxne.covilo.shared;

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
   * Checks whether the provided character sequence is empty or consists only
   * of whitespace characters.
   *
   * @param sequence The character sequence to check.
   * @throws NullPointerException     If the provided sequence is null.
   * @throws IllegalArgumentException If the sequence is empty or consists only
   *                                  of whitespace characters.
   */
  public static void empty(
    final CharSequence sequence
  ) {
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

  /**
   * Checks whether the provided iterable is empty.
   *
   * @param iterable The iterable to check.
   * @throws NullPointerException     If the provided iterable is null.
   * @throws IllegalArgumentException If the iterable is empty.
   */
  public static void empty(
    final Iterable<?> iterable
  ) {
    Validation.check(iterable);

    if (!iterable.iterator().hasNext()) {
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
