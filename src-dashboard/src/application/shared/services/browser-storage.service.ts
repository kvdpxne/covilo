import {Injectable} from "@angular/core";
import {BrowserStorageKey} from "./browser-storage-key";

/**
 * Injectable service for interacting with browser storage.
 *
 * This service provides methods to store, retrieve, and delete data from
 * both `localStorage` and `sessionStorage` in the browser.
 */
@Injectable({
  providedIn: "root"
})
export class BrowserStorageService {

  /**
   * Checks if the given key exists in the specified storage.
   *
   * @param storage The storage to check (`localStorage` or `sessionStorage`).
   * @param key The key to check.
   * @returns A boolean indicating whether the key exists in the storage.
   */
  private hasIn(
    storage: Storage,
    key: BrowserStorageKey | string
  ): boolean {
    return null != storage.getItem(key);
  }

  /**
   * Checks if the given key exists in the local storage.
   *
   * @param key The key to check.
   * @returns A boolean indicating whether the key exists in the local storage.
   */
  public hasInLocalStorage(
    key: BrowserStorageKey | string
  ): boolean {
    return this.hasIn(
      window.localStorage,
      key
    );
  }

  /**
   * Checks if the given key exists in the session storage.
   *
   * @param key The key to check.
   * @returns A boolean indicating whether the key exists in the session storage.
   */
  public hasInSessionStorage(
    key: BrowserStorageKey | string
  ): boolean {
    return this.hasIn(
      window.sessionStorage,
      key
    );
  }

  /**
   * Loads data from the specified storage.
   *
   * @param storage The storage to load data from (`localStorage` or `sessionStorage`).
   * @param key The key to load data for.
   * @returns The loaded data, or `null` if not found or unable to parse.
   *
   * This method retrieves the data associated with the provided key from the specified storage.
   * It first checks if the data exists using the key. If the data exists, it attempts to parse the
   * stored string value into its original type. If successful, it returns the parsed data; otherwise,
   * it returns `null`.
   *
   * Note: If the data cannot be parsed due to invalid JSON format or other reasons, this method
   * handles the error and returns `null`.
   */
  private loadFrom<T>(
    storage: Storage,
    key: BrowserStorageKey | string
  ): T | null {
    // Retrieve the string value associated with the key from storage
    const textValue: string | null = storage.getItem(key);

    // Check if no data found for the key
    if (!textValue) {
      // If no data found for the key, return null
      return null;
    }

    try {
      // Attempt to parse the stored string value into its original type
      return JSON.parse(textValue) as T;
    } catch (error) {
      // If parsing fails, return null
      return null;
    }
  }

  /**
   * Loads data from local storage.
   *
   * @param key The key to load data for.
   * @returns The loaded data, or `null` if not found or unable to parse.
   */
  public loadFromLocalStorage<T>(
    key: BrowserStorageKey | string
  ): T | null {
    return this.loadFrom<T>(
      window.localStorage,
      key
    );
  }

  /**
   * Loads data from session storage.
   *
   * @param key The key to load data for.
   * @returns The loaded data, or `null` if not found or unable to parse.
   */
  public loadFromSessionStorage<T>(
    key: BrowserStorageKey
  ): T | null {
    return this.loadFrom<T>(
      window.sessionStorage,
      key
    );
  }

  /**
   * Stores data in the specified storage.
   *
   * @param storage The storage to store data in (`localStorage` or `sessionStorage`).
   * @param key The key to store data for.
   * @param value The value to store.
   * @param force Indicates whether to forcefully overwrite existing data.
   * @returns A boolean indicating whether the operation was successful.
   * @throws Error if the value passed is `null` or `undefined`.
   */
  private storeIn<T>(
    storage: Storage,
    key: BrowserStorageKey | string,
    value: T,
    force: boolean = false
  ): boolean {
    // Ensure value is not null or undefined
    if (!value) {
      throw Error("The value passed to storage cannot be null or undefined.");
    }

    // Check if the key already exists unless force overwrite is requested
    if (!force && this.hasIn(storage, key)) {
      return false;
    }

    let textValue: string;
    switch (typeof value) {
      case "string":
        // If the value is already a string, no conversion needed
        textValue = value;
        break;
      case "boolean":
      case "bigint":
      case "number":
        // Convert non-string values to string
        textValue = String(value);
        break;
      case "object":
        // Convert objects to JSON string
        textValue = JSON.stringify(value);
        break;
      default:
        // Throw error for unsupported types
        const typeOfValue: any = typeof (value);
        throw Error(`Type "${typeOfValue}" cannot be stored.`);
    }

    // Store the string representation of the value in storage
    storage.setItem(key, textValue);
    return true;
  }

  /**
   * Stores data in local storage.
   *
   * @param key The key to store data for.
   * @param value The value to store.
   * @param force Indicates whether to forcefully overwrite existing data.
   * @returns A boolean indicating whether the operation was successful.
   * @throws Error if the value passed is `null` or `undefined`.
   */
  public storeInLocalStorage<T>(
    key: BrowserStorageKey | string,
    value: T,
    force: boolean = false
  ): boolean {
    return this.storeIn<T>(
      window.localStorage,
      key,
      value,
      force
    );
  }

  /**
   * Stores data in session storage.
   *
   * @param key The key to store data for.
   * @param value The value to store.
   * @param force Indicates whether to forcefully overwrite existing data.
   * @returns A boolean indicating whether the operation was successful.
   * @throws Error if the value passed is `null` or `undefined`.
   */
  public storeInSessionStorage<T>(
    key: BrowserStorageKey | string,
    value: T,
    force: boolean = false
  ): boolean {
    return this.storeIn<T>(
      window.sessionStorage,
      key,
      value,
      force
    );
  }

  /**
   * Deletes data from the specified storage.
   *
   * @param storage The storage to delete data from (`localStorage` or `sessionStorage`).
   * @param key The key to delete data for.
   */
  private deleteFrom(
    storage: Storage,
    key: BrowserStorageKey | string
  ): void {
    storage.removeItem(key);
  }

  /**
   * Deletes data from local storage.
   * @param key The key to delete data for.
   */
  public deleteFromLocalStorage(
    key: BrowserStorageKey | string
  ): void {
    this.deleteFrom(
      window.localStorage,
      key
    );
  }

  /**
   * Deletes data from session storage.
   * @param key The key to delete data for.
   */
  public deleteFromSessionStorage(
    key: BrowserStorageKey | string
  ): void {
    this.deleteFrom(
      window.sessionStorage,
      key
    );
  }
}