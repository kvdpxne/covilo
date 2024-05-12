package me.kvdpxne.covilo.shared;

import java.util.function.Supplier;

public final class Validation {

  public static final String REQUIRED_NOT_NULL_MESSAGE
    = "The value passed to be checked cannot be null.";

  public static final String REQUIRED_TRUE_CONDITION_MESSAGE
    = "The required condition must be true.";

  /**
   * Private constructor to prevent instantiation of the class.
   * Throws an {@link ObjectInitializationError} to indicate that
   * this class should not be initialized.
   */
  private Validation() {
    throw new ObjectInitializationError();
  }

  /**
   * Checks if the given value is not {@code null}. If it is {@code null}, a
   * {@link NullPointerException} is thrown with the message obtained from
   * the message supplier. If the message supplier is {@code null}, a default
   * message is used.
   *
   * @param <T>             The type of the value being checked.
   * @param value           The value to be checked for {@code null}.
   * @param messageSupplier A supplier providing the message to be used in
   *                        the exception if the value is {@code null}.
   * @return The value if it is not {@code null}.
   * @throws NullPointerException If the value is {@code null}.
   */
  public static <T> T check(
    final T value,
    final Supplier<? extends String> messageSupplier
  ) {
    if (null == value) {
      throw null != messageSupplier
        ? new NullPointerException(messageSupplier.get())
        : new NullPointerException(REQUIRED_NOT_NULL_MESSAGE);
    }
    return value;
  }

  /**
   * Checks if the given value is not {@code null}. If it is {@code null},
   * throws a {@link NullPointerException} with the provided message.
   *
   * @param <T>     The type of the value being checked.
   * @param value   The value to be checked for {@code null}.
   * @param message The message to be used in the exception if the value is
   *                {@code null}.
   * @return The value if it is not {@code null}.
   * @throws NullPointerException If the value is {@code null}.
   * @see Validation#check(Object, Supplier)
   */
  public static <T> T check(
    final T value,
    final String message
  ) {
    return Validation.check(
      value,
      () -> message
    );
  }

  /**
   * Checks if the given value is not {@code null}. If it is {@code null},
   * throws a {@link NullPointerException} with a default message.
   *
   * @param <T>   The type of the value being checked.
   * @param value The value to be checked for {@code null}.
   * @return The value if it is not {@code null}.
   * @throws NullPointerException If the value is {@code null}.
   * @see Validation#check(Object, Supplier)
   */
  public static <T> T check(
    final T value
  ) {
    return Validation.check(
      value,
      () -> REQUIRED_NOT_NULL_MESSAGE
    );
  }

  /**
   * Checks the given condition. If the condition is {@code false}, throws an
   * {@link IllegalArgumentException} with the message obtained from the
   * message supplier. If the message supplier is {@code null}, a default
   * message is used.
   *
   * @param condition       The condition to be checked.
   * @param messageSupplier A supplier providing the message to be used in
   *                        the exception if the condition is {@code false}.
   * @throws IllegalArgumentException If the condition is {@code false}.
   */
  public static void check(
    final boolean condition,
    final Supplier<? extends String> messageSupplier
  ) {
    if (!condition) {
      throw null != messageSupplier
        ? new IllegalArgumentException(messageSupplier.get())
        : new IllegalArgumentException(REQUIRED_TRUE_CONDITION_MESSAGE);
    }
  }

  /**
   * Checks the given condition. If the condition is {@code false}, throws an
   * {@link IllegalArgumentException} with the provided message.
   *
   * @param condition The condition to be checked.
   * @param message   The message to be used in the exception if the condition
   *                  is {@code false}.
   * @throws IllegalArgumentException If the condition is {@code false}.
   * @see Validation#check(boolean, Supplier)
   */
  public static void check(
    final boolean condition,
    final String message
  ) {
    Validation.check(
      condition,
      () -> message
    );
  }

  /**
   * Checks the given condition. If the condition is {@code false}, throws an
   * {@link IllegalArgumentException} with a default message.
   *
   * @param condition The condition to be checked.
   * @throws IllegalArgumentException If the condition is {@code false}.
   * @see Validation#check(boolean, Supplier)
   */
  public static void check(
    final boolean condition
  ) {
    Validation.check(
      condition,
      () -> REQUIRED_TRUE_CONDITION_MESSAGE
    );
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
}
