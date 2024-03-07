import {Injectable} from "@angular/core";

/**
 * This service provides in-memory storage functionality for storing,
 * retrieving, and removing data.
 */
@Injectable({
  providedIn: "root"
})
export class InMemoryStorageService {

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
   * Saves the provided value under the given key in the storage.
   * @param key The key under which the value will be saved.
   * @param value The value to be saved.
   */
  public store<T>(key: string, value: T): void {
    this.storage[key] = value;
  }

  /**
   * Retrieves the value associated with the provided key from the storage.
   * @param key The key of the value to retrieve.
   * @returns The value associated with the provided key.
   */
  public get<T>(key: string): T {
    return this.storage[key];
  }

  /**
   * Removes the value associated with the provided key from the storage.
   * @param key The key of the value to remove.
   */
  public remove(key: string): void {
    delete this.storage[key];
  }

  /**
   * Clears all stored key-value pairs from the storage.
   */
  public clear(): void {
    this.storage = {};
  }
}
