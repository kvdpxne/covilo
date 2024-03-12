import {Injectable} from "@angular/core";
import {Storage} from "./storage";
import {StorageKey} from "./storage-key";
import {LoggingService} from "./logging.service";

/**
 * This service provides in-memory storage functionality for storing,
 * retrieving, and removing data.
 */
@Injectable({
  providedIn: "root"
})
export class InMemoryStorageService
  implements Storage {

  private readonly loggingService: LoggingService;

  /**
   * Internal storage object to hold key-value pairs.
   */
  private storage: { [key: string]: any };

  /**
   * Constructs a new InMemoryStorageService with an empty storage object.
   */
  public constructor(
    loggingService: LoggingService
  ) {
    this.loggingService = loggingService;
    this.storage = {};
  }

  /**
   * Retrieves the value associated with the provided key from the storage.
   * @param key The key of the value to retrieve.
   * @returns The value associated with the provided key.
   */
  public get<T>(key: StorageKey): T {
    return this.storage[key];
  }

  public has(key: StorageKey): boolean {
    return null != this.get(key);
  }

  /**
   * Saves the provided value under the given key in the storage.
   * @param key The key under which the value will be saved.
   * @param value The value to be saved.
   */
  public store<T>(
    key: StorageKey,
    value: T,
    force: boolean = false
  ): boolean {
    const present = this.has(key);
    if (!force && present) {
      return false;
    }
    this.storage[key] = value;
    this.loggingService.debug(() => present
      ? `A new value was assigned to the ${key} key in the memory store.`
      : `The ${key} key value was overwritten in the memory store.`
    );
    return true;
  }

  /**
   * Removes the value associated with the provided key from the storage.
   * @param key The key of the value to remove.
   */
  public remove(key: StorageKey | string): void {
    delete this.storage[key];
  }

  /**
   * Clears all stored key-value pairs from the storage.
   */
  public clear(): void {
    this.storage = {};
  }
}
