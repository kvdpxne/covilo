package me.kvdpxne.covilo.shared;

public final class Validation {

  public static void check(final Object value, final String message) {
    if (null == value) {
      throw new NullPointerException();
    }
  }

  public static void check(final Object value) {
    Validation.check(value, "The given parameter cannot be null.");
  }

  public static void check(final boolean condition, final String message) {
    if (condition) {
      throw new IllegalArgumentException(message);
    }
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

  private Validation() {
    throw new Error("The utility object should not be initialized.");
  }
}
