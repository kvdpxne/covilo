import {Injectable} from "@angular/core";
import {StorageKey} from "./storage-key";

@Injectable({
  providedIn: "root"
})
export class StorageService {

  public getValue(key: StorageKey | string): string | null {
    return localStorage.getItem(key);
  }

  public setValue(key: StorageKey, value: string): void {
    localStorage.setItem(key, value);
  }

  public has(key: StorageKey | string): boolean {
    return !this.getValue(key);
  }
}
