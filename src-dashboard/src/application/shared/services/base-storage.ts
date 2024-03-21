import {Storage} from "./storage";
import {StorageKey} from "./storage-key";

export abstract class BaseStorage
  implements Storage {

  public abstract all(): Map<string, any>

  public abstract get<T>(
    key: string
  ): T | null

  public abstract has(
    key: string
  ): boolean

  public abstract store<T>(
    key: string,
    value: T,
    force: boolean
  ): boolean

  public abstract remove(
    key: string
  ): void

  public transfer(
    to: Storage,
    keys: StorageKey[] | string[] = []
  ): void {
    if (!to) {
      throw Error("")
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

  public abstract clear(): void
}