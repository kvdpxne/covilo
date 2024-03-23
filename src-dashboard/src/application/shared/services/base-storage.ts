import {Storage} from "./storage";
import {StorageKey} from "./storage-key";
import {Logger} from "./logger.service";

/**
 * Represents an abstract base class for implementing Storage interfaces.
 * Provides common functionality for storage operations.
 */
export abstract class BaseStorage
  implements Storage {

  /**
   * The logging service used for logging messages related to storage
   * operations.
   */
  protected readonly logger: Logger;

  /**
   * Constructs a new BaseStorage.
   *
   * @param logger The logging service to use for logging messages
   * related to storage operations.
   */
  protected constructor(
    logger: Logger
  ) {
    this.logger = logger;
  }

  /**
   * Retrieves all key-value pairs from the storage.
   *
   * @returns A Map containing all key-value pairs stored in the storage.
   */
  public abstract all(): Map<StorageKey | string, any>

  /**
   * Retrieves the value associated with the specified key from the storage.
   *
   * @param key The key to retrieve the value for.
   * @returns The value associated with the key, or null if the key doesn't exist.
   */
  public abstract get<T>(
    key: StorageKey | string
  ): T | null

  /**
   * Checks if the storage contains a value associated with the specified key.
   *
   * @param key The key to check for existence.
   * @returns True if the storage contains the key, otherwise false.
   */
  public abstract has(
    key: StorageKey | string
  ): boolean

  /**
   * Stores the specified value with the given key in the storage.
   *
   * @param key The key to store the value under.
   * @param value The value to be stored.
   * @param force If true, overwrites any existing value associated with the key.
   * @returns True if the value was successfully stored, otherwise false.
   */
  public abstract store<T>(
    key: StorageKey | string,
    value: T,
    force: boolean
  ): boolean

  /**
   * Removes the value associated with the specified key from the storage.
   *
   * @param key The key of the value to remove.
   */
  public abstract remove(
    key: StorageKey | string
  ): void

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
  public transfer(
    to: Storage,
    keys: StorageKey[] | string[] = []
  ): void {
    // Check if the destination storage is provided
    if (!to) {
      throw Error("Destination storage must be provided for transfer.");
    }
    // If no specific keys are provided, transfer all values from this
    // storage to the destination storage
    if (0 === keys.length) {
      // Retrieve all key-value pairs from this storage
      this.all().forEach((value, key) => {
        // Store each key-value pair in the destination storage, overwriting
        // any existing value with the same key
        to.store(key, value, true);
      });
      return;
    }
    // Transfer only the specified keys and their corresponding values from
    // this storage to the destination storage
    for (const key of keys) {
      // Retrieve the value associated with the current key from this storage
      const value = this.get<any>(key);
      // If the value is not found for the current key, skip to the next key
      if (!value) {
        continue;
      }
      // Store the key-value pair in the destination storage, overwriting
      // any existing value with the same key
      to.store(key, value, true);
    }
  }

  /**
   * Clears all values from the storage.
   */
  public abstract clear(): void
}