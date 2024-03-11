import {Injectable} from "@angular/core";
import {StorageKey} from "./storage-key";
import {Storage} from "./storage";

/**
 * Injectable service for interacting with browser storage.
 *
 * This service provides methods to store, retrieve, and delete data from
 * both `localStorage` and `sessionStorage` in the browser.
 */
@Injectable({
  providedIn: "root"
})
export class BrowserStorageService
  implements Storage {

  public get<T>(key: StorageKey): T | null {
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

  public has(key: StorageKey): boolean {
    return null != window.localStorage.getItem(key);
  }

  public store<T>(key: StorageKey, value: T, force: boolean = true): boolean {
    // Ensure value is not null or undefined
    if (!value) {
      throw Error("The value passed to storage cannot be null or undefined.");
    }

    // Check if the key already exists unless force overwrite is requested
    if (!force && this.has(key)) {
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
    return true;
  }

  public remove(key: StorageKey): void {
    window.localStorage.removeItem(key);
  }

  public clear(): void {
    window.localStorage.clear();
  }
}