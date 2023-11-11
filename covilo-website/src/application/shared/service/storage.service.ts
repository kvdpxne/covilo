import {Injectable} from "@angular/core";
import {StorageKey} from "./storage-key";
import {StorageType} from "./storage-type";

@Injectable({
  providedIn: "root"
})
export class StorageService {

  public getValue(key: StorageKey | string): string | null {
    return localStorage.getItem(key);
  }

  public load(
    key: StorageKey | string,
    type: StorageType = StorageType.LOCAL_STORAGE
  ): any {
    let textValue: string | null;
    switch (type) {
      case StorageType.SESSION_STORAGE:
        textValue = sessionStorage.getItem(key);
        break;
      case StorageType.LOCAL_STORAGE:
        textValue = localStorage.getItem(key);
        break;
      default:
        throw Error("Unknown storage type.");
    }

    if (!textValue) {
      return textValue;
    }

    switch (typeof textValue) {
      case "string":
        return textValue;
      case "boolean":
        return !!textValue;
      case "bigint":
        return BigInt(textValue);
      case "number":
        return +textValue;
      case "object":
        return JSON.parse(textValue);
      default:
        return null;
    }
  }

  public setValue(
    key: StorageKey | string,
    value: any,
    type: StorageType = StorageType.LOCAL_STORAGE
  ): void {
    if (!value) {
      throw Error("The value passed to storage cannot be null or undefined.");
    }

    let textValue: string;
    switch (typeof value) {
      case "string":
        // There is no need to do anything here because the store only accepts
        // values that are strings and the given value is already a string.
        textValue = value;
        break;
      case "boolean":
      case "bigint":
      case "number":
        textValue = String(value);
        break;
      case "object":
        textValue = JSON.stringify(value);
        break;
      default:
        const typeOfValue: any = typeof (value);
        throw Error(`Type "${typeOfValue}" cannot be stored.`);
    }

    switch (type) {
      case StorageType.SESSION_STORAGE:
        sessionStorage.setItem(key, textValue);
        break;
      case StorageType.LOCAL_STORAGE:
        localStorage.setItem(key, textValue);
        break;
      default:
        throw Error("Unknown storage type.");
    }
  }

  public has(key: StorageKey | string): boolean {
    return !this.getValue(key);
  }
}
