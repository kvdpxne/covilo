import {Injectable} from "@angular/core";
import {AuthenticationStrategy} from "./authentication-strategy";
import {
  BrowserStorageService,
  InMemoryStorageService,
  StorageKey
} from "../../shared";
import {Token} from "../../core";
import {Storage} from "../../shared/services/storage";

@Injectable({
  providedIn: "root"
})
export class TokenAuthenticationStrategy
  implements AuthenticationStrategy<Token> {

  private readonly storageService: Storage;

  public constructor(
    inMemoryStorageService: InMemoryStorageService
  ) {
    this.storageService = inMemoryStorageService;
  }

  public getToken(): Token | null {
    return this.storageService.get<Token>(StorageKey.TOKEN);
  }

  public isLogged(): boolean {
    return this.storageService.has(StorageKey.TOKEN);
  }

  public doLogin(token: Token): void {
    this.storageService.store(StorageKey.TOKEN, token, true);
  }

  public doLogout(): void {
    this.storageService.remove(StorageKey.TOKEN);
  }
}
