package me.kvdpxne.covilo.infrastructure.uid;

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
   * This method uses the {@code UlidCreator} library to generate a new ULID and
   * converts it to a lowercase string. ULIDs are 128-bit identifiers that are
   * lexicographically sortable and URL-safe.
   * </p>
   *
   * @return a new ULID in lowercase string format.
   */
  public static String next() {
    return UlidCreator.getUlid().toString();
  }
}
