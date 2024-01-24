import {AuthenticationStrategy} from "./authentication.strategy";
import {Token} from "../../core";
import {EMPTY, Observable} from "rxjs";
import {StorageKey, StorageService} from "../../shared";

export class TokenAuthenticationStrategy
  implements AuthenticationStrategy<Token> {

  private readonly storageService: StorageService;

  public constructor(storageService: StorageService) {
    this.storageService = storageService;
  }

  public getToken(): Token | null {
    return this.storageService.load<Token>(StorageKey.TOKEN);
  }

  public getCurrentUser(): Observable<never> {
    return EMPTY;
  }

  public isLogged(): boolean {
    // Checks whether the given key exists in the store but does not check
    // whether anything is assigned to this key.
    return this.storageService.has(StorageKey.TOKEN);
  }

  public doLogin(data: Token): void {
    this.storageService.store<Token>(StorageKey.TOKEN, data);
  }

  public doLogout(): void {
    this.storageService.delete(StorageKey.TOKEN);
  }
}