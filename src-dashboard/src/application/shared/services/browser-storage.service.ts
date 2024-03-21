import {Injectable} from "@angular/core";
import {StorageKey} from "./storage-key";
import {Logger} from "./logger.service";
import {BaseStorage} from "./base-storage";

/**
 * Injectable service for interacting with browser storage.
 *
 * This service provides methods to store, retrieve, and delete data from
 * both `localStorage` and `sessionStorage` in the browser.
 */
@Injectable({
  providedIn: "root"
})
export class BrowserStorage
  extends BaseStorage {

  /**
   * The logging service used for logging messages related to storage
   * operations.
   */
  private readonly logger: Logger;

  /**
   * Constructs a new BrowserStorageService.
   *
   * @param logger The logging service to use for logging messages
   * related to storage operations.
   */
  public constructor(
    logger: Logger
  ) {
    super();
    this.logger = logger;
  }

  public override all(): Map<StorageKey | string, any> {
    return new Map(Object.entries(window.localStorage))
  }

  /**
   * Retrieves the value associated with the provided key from the storage.
   *
   * @param key The key of the value to retrieve.
   * @returns The value associated with the provided key.
   */
  public override get<T>(
    key: StorageKey | string
  ): T | null {
    // Retrieve the string value associated with the key from storage
    const textValue = window.localStorage.getItem(key);

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
   * Checks if the storage contains a value associated with the specified key.
   *
   * @param key The key to check for existence.
   * @returns True if the storage contains the key, otherwise false.
   */
  public override has(
    key: StorageKey | string
  ): boolean {
    return null != window.localStorage.getItem(key);
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
  public override store<T>(
    key: StorageKey | string,
    value: T,
    force: boolean = true
  ): boolean {
    // Ensure value is not null or undefined
    if (!value) {
      throw Error("The value passed to storage cannot be null or undefined.");
    }

    const present = this.has(key);
    // Check if the key already exists unless force overwrite is requested
    if (!force && present) {
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
    window.localStorage.setItem(key, textValue);
    this.logger.debug(() => present
      ? `A new value was assigned to the ${key} key in the browser store.`
      : `The ${key} key value was overwritten in the browser store.`
    );
    return true;
  }

  /**
   * Removes the value associated with the provided key from the storage.
   *
   * @param key The key of the value to remove.
   */
  public override remove(
    key: StorageKey | string
  ): void {
    window.localStorage.removeItem(key);
    this.logger.debug(() =>
      `The value assigned to the ${key} key has been deleted.`
    );
  }

  /**
   * Clears all stored key-value pairs from the storage.
   */
  public override clear(): void {
    window.localStorage.clear();
  }
}