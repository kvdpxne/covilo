package me.kvdpxne.covilo.shared;

public class Validation {

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
}
