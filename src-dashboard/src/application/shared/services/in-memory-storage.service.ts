import {Injectable} from "@angular/core";
import {Storage} from "./storage";
import {StorageKey} from "./storage-key";
import {Logger} from "./logger.service";

/**
 * This service provides in-memory storage functionality for storing,
 * retrieving, and removing data.
 */
@Injectable({
  providedIn: "root"
})
export class InMemoryStorage
  implements Storage {

  /**
   * The logging service used for logging messages related to storage
   * operations.
   */
  private readonly logger: Logger;

  /**
   * Internal storage object to hold key-value pairs.
   */
  private storage: {
    [key: string]: any
  };

  /**
   * Constructs a new InMemoryStorageService with an empty storage object.
   *
   * @param logger The logging service to use for logging messages.
   */
  public constructor(
    logger: Logger
  ) {
    this.logger = logger;
    this.storage = {};
  }

  public all(): Map<StorageKey | string, any> {
    return new Map(Object.entries(this.storage));
  }

  /**
   * Retrieves the value associated with the provided key from the storage.
   *
   * @param key The key of the value to retrieve.
   * @returns The value associated with the provided key.
   */
  public get<T>(
    key: StorageKey | string
  ): T {
    return this.storage[key];
  }

  /**
   * Checks if the storage contains a value associated with the specified key.
   *
   * @param key The key to check for existence.
   * @returns True if the storage contains the key, otherwise false.
   */
  public has(
    key: StorageKey | string
  ): boolean {
    return null != this.get(key);
  }

  /**
   * Saves the provided value under the given key in the storage.
   *
   * @param key The key under which the value will be saved.
   * @param value The value to be saved.
   * @param force If true, overwrites any existing value associated with the
   * key.
   * @returns True if the value was successfully stored, otherwise false.
   * @throws Error if the value passed is null or undefined.
   */
  public store<T>(
    key: StorageKey | string,
    value: T,
    force: boolean = false
  ): boolean {
    // Ensure value is not null or undefined
    if (!value) {
      throw Error("The value passed to storage cannot be null or undefined.");
    }

    const present = this.has(key);
    if (!force && present) {
      return false;
    }
    this.storage[key] = value;
    this.logger.debug(() => present
      ? `A new value was assigned to the ${key} key in the memory store.`
      : `The ${key} key value was overwritten in the memory store.`
    );
    return true;
  }

  /**
   * Removes the value associated with the provided key from the storage.
   *
   * @param key The key of the value to remove.
   */
  public remove(
    key: StorageKey | string
  ): void {
    delete this.storage[key];
    this.logger.debug(() =>
      `The value assigned to the ${key} key has been deleted.`
    );
  }

  transfer(
    to: Storage,
    ...keys: StorageKey[] | string[]
  ): void {
    if (!to) {
      throw Error("");
    }

    if (0 === keys.length) {
      this.all().forEach((value, key) => {
        to.store(key, value, true);
      });
      return;
    }

    for (const key of keys) {
      const value = this.get<any>(key);
      if (!value) {
        continue;
      }
      to.store(key, value, true);
    }
  }


  /**
   * Clears all stored key-value pairs from the storage.
   */
  public clear(): void {
    this.storage = {};
  }
}
