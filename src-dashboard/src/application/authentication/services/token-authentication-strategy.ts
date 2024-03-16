import {Injectable} from "@angular/core";
import {AuthenticationStrategy} from "./authentication-strategy";
import {InMemoryStorage, Storage, StorageKey} from "../../shared";
import {Token} from "../../core";

@Injectable({
  providedIn: "root"
})
export class TokenAuthenticationStrategy
  implements AuthenticationStrategy<Token> {

  private readonly storage: Storage;

  public constructor(
    inMemoryStorage: InMemoryStorage
  ) {
    this.storage = inMemoryStorage;
  }

  public getToken(): Token | null {
    return this.storage.get<Token>(StorageKey.TOKEN);
  }

  public isLogged(): boolean {
    return this.storage.has(StorageKey.TOKEN);
  }

  public doLogin(token: Token): void {
    this.storage.store(StorageKey.TOKEN, token, true);
  }

  public doLogout(): void {
    this.storage.remove(StorageKey.TOKEN);
  }
}
