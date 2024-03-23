import {StorageKey} from "./storage-key";

/**
 * Represents a Storage interface for managing data storage.
 */
export interface Storage {

  /**
   * Retrieves all key-value pairs from the storage.
   *
   * @returns A Map containing all key-value pairs stored in the storage.
   */
  all(): Map<StorageKey | string, any>;

  /**
   * Retrieves the value associated with the specified key from the storage.
   *
   * @param key The key to retrieve the value for.
   * @returns The value associated with the key, or null if the key doesn't
   * exist.
   */
  get<T>(
    key: StorageKey | string
  ): T | null;

  /**
   * Checks if the storage contains a value associated with the specified key.
   *
   * @param key The key to check for existence.
   * @returns True if the storage contains the key, otherwise false.
   */
  has(
    key: StorageKey | string
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
    key: StorageKey | string,
    value: T,
    force: boolean
  ): boolean;

  /**
   * Removes the value associated with the specified key from the storage.
   *
   * @param key The key of the value to remove.
   */
  remove(
    key: StorageKey | string
  ): void;

  /**
   * Transfers specified keys along with their values from this storage to
   * another storage.
   *
   * If `keys` is an empty array, transfer all values from this storage to
   * another storage.
   *
   * @param to The storage to transfer the keys and values to.
   * @param keys An array of keys or StorageKey objects to be transferred.
   * If not provided or an empty array, all values from this storage will be
   * transferred.
   * @throws Error if the destination storage (`to`) is not provided.
   */
  transfer(
    to: Storage,
    keys: StorageKey[] | string[]
  ): void;

  /**
   * Clears all values from the storage.
   */
  clear(): void;
}
