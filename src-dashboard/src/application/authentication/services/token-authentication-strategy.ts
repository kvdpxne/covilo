import {Injectable} from "@angular/core";
import {AuthenticationStrategy} from "./authentication-strategy";
import {BrowserStorage, InMemoryStorage, StorageKey} from "../../shared";
import {Token} from "../../core";

@Injectable({
  providedIn: "root"
})
export class TokenAuthenticationStrategy
  implements AuthenticationStrategy<Token> {

  private readonly browserStorage: BrowserStorage;

  private readonly inMemoryStorage: InMemoryStorage;

  public constructor(
    browserStorage: BrowserStorage,
    inMemoryStorage: InMemoryStorage
  ) {
    this.browserStorage = browserStorage;
    this.inMemoryStorage = inMemoryStorage;
  }

  public getToken(): Token | null {
    return this.inMemoryStorage.get<Token>(StorageKey.USER_TOKEN);
  }

  public isLogged(): boolean {
    return this.browserStorage.get<boolean>(StorageKey.USER_IS_AUTHENTICATED)
      || this.inMemoryStorage.has(StorageKey.USER_TOKEN);
  }

  public doLogin(token: Token): void {
    this.inMemoryStorage.store(StorageKey.USER_TOKEN, token, true);
  }

  public doLogout(): void {
    this.inMemoryStorage.remove(StorageKey.USER_TOKEN);
  }
}
