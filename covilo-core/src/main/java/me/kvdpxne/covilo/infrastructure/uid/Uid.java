package me.kvdpxne.covilo.infrastructure.uid;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

/**
 * Utility class for generating unique identifiers using ULID (Universally
 * Unique Lexicographically Sortable Identifier).
 * <p>
 * This class provides a method to generate a new ULID and convert it to a
 * lowercase string representation. The generated ULIDs are globally unique and
 * can be used as identifiers for various purposes such as database keys,
 * transaction IDs, etc.
 * </p>
 *
 * @see <a href="https://github.com/f4b6a3/ulid-creator">ULID Creator</a>
 * @since 0.1
 */
public final class Uid {

  /**
   * Generates a new ULID and returns its string representation in lowercase.
   * <p>
   * This method uses the {@link UlidCreator} library to generate a new ULID and
   * converts it to a lowercase string.
   * </p>
   *
   * @return a new ULID in lowercase string format.
   */
  public static String next() {
    return UlidCreator.getUlid().toLowerCase();
  }

  /**
   * Generates a new monotonic ULID and returns its string representation in
   * lowercase.
   * <p>
   * This method uses the {@link UlidCreator} library to generate a new
   * monotonic ULID and converts it to a lowercase string. The random component
   * is incremented by 1 whenever the current millisecond equals the previous
   * millisecond. Its main advantage is speed.
   * </p>
   *
   * @return a new monotonic ULID in lowercase string format.
   */
  public static String nextMonotonic() {
    return UlidCreator.getMonotonicUlid().toLowerCase();
  }

  /**
   * Generates a new fast ULID and returns its string representation in
   * lowercase.
   * <p>
   * This method uses the {@link Ulid#fast()} method to quickly generate a ULID
   * and converts it to a lowercase string. This method is faster but may trade
   * off some randomness or other attributes.
   * </p>
   *
   * @return a new fast ULID in lowercase string format.
   */
  public static String fast() {
    return Ulid.fast().toLowerCase();
  }
}
