import {Injectable} from "@angular/core";
import {StorageKey} from "./storage-key";
import {StorageType} from "./storage-type";

@Injectable({
  providedIn: "root"
})
export class StorageService {

  public load<T>(
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
        return JSON.parse(textValue) as T;
      default:
        return null;
    }
  }

  public store<T>(
    key: StorageKey | string,
    value: T,
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

  public delete(
    key: StorageKey | string,
    type: StorageType = StorageType.LOCAL_STORAGE
  ): void {
    switch (type) {
      case StorageType.LOCAL_STORAGE:
        localStorage.removeItem(key);
        break;
      case StorageType.SESSION_STORAGE:
        localStorage.removeItem(key);
        return;
      default:
        throw Error("Unknown storage type.");
    }
  }

  private inStorage(key: StorageKey | string, storage: Storage): boolean {
    const length: number = storage.length;
    if (0 >= length) {
      return false;
    }
    for (let i: number = 0; i < length; i++) {
      if (key === localStorage.key(i)) {
        return true;
      }
    }
    return false;
  }

  public has(
    key: StorageKey | string,
    type: StorageType = StorageType.LOCAL_STORAGE
  ): boolean {
    switch (type) {
      case StorageType.LOCAL_STORAGE:
        return this.inStorage(key, localStorage);
      case StorageType.SESSION_STORAGE:
        return this.inStorage(key, sessionStorage);
      default:
        return false;
    }
  }
}
