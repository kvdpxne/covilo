import {StorageKey} from "./storage-key";

/**
 * Represents a Storage interface for managing data storage.
 */
export interface Storage {

  all(): Map<StorageKey | string, any>

  /**
   * Retrieves the value associated with the specified key from the storage.
   *
   * @param key The key to retrieve the value for.
   * @returns The value associated with the key, or null if the key doesn't
   * exist.
   */
  get<T>(
    key: StorageKey
  ): T | null;

  /**
   * Checks if the storage contains a value associated with the specified key.
   *
   * @param key The key to check for existence.
   * @returns True if the storage contains the key, otherwise false.
   */
  has(
    key: StorageKey
  ): boolean;

  /**
   * Stores the specified value with the given key in the storage.
   *
   * @param key The key to store the value under.
   * @param value The value to be stored.
   * @param force If true, overwrites any existing value associated with the
   * key.
   * @returns True if the value was successfully stored, otherwise false.
   */
  store<T>(
    key: StorageKey,
    value: T,
    force: boolean
  ): boolean;

  /**
   * Removes the value associated with the specified key from the storage.
   *
   * @param key The key of the value to remove.
   */
  remove(
    key: StorageKey
  ): void;

  /**
   * Clears all values from the storage.
   */
  clear(): void;
}
