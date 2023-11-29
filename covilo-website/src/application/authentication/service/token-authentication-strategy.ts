import {AuthenticationStrategy} from "./authentication-strategy";
import {Token, User} from "../../core";
import {Observable, of} from "rxjs";
import {StorageKey, StorageService} from "../../shared";

export class TokenAuthenticationStrategy
  implements AuthenticationStrategy<Token> {

  private readonly storageService: StorageService;

  constructor(storageService: StorageService) {
    this.storageService = storageService;
  }

  getToken(): Token | null {
    return this.storageService.load<Token>(StorageKey.TOKEN);
  }

  getCurrentUser(): Observable<User | null> {
    const token: Token | null = this.getToken();

    if (token) {
      const encodedPayload: string = token.compactAccessToken.split(".")[1];
      const payload: string = window.atob(encodedPayload);
      return of(JSON.parse(payload));
    }
    return of(null);
  }

  isLogged(): boolean {
    // Checks whether the given key exists in the store but does not check
    // whether anything is assigned to this key.
    return this.storageService.has(StorageKey.TOKEN);
  }

  doLogin(token: Token): void {
    this.storageService.store<Token>(StorageKey.TOKEN, token);
    // console.log(this.storageService.load(StorageKey.TOKEN));
  }

  doLogout(): void {
    this.storageService.delete(StorageKey.TOKEN);
  }
}