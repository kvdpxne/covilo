import {Injectable} from "@angular/core";
import {Storage} from "./storage";
import {StorageKey} from "./storage-key";

/**
 * This service provides in-memory storage functionality for storing,
 * retrieving, and removing data.
 */
@Injectable({
  providedIn: "root"
})
export class InMemoryStorageService
  implements Storage {

  /**
   * Internal storage object to hold key-value pairs.
   */
  private storage: { [key: string]: any };

  /**
   * Constructs a new InMemoryStorageService with an empty storage object.
   */
  public constructor() {
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
  public store<T>(key: StorageKey, value: T, force: boolean = true): boolean {
    if (!force && this.has(key)) {
      return false;
    }

    this.storage[key] = value;
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
