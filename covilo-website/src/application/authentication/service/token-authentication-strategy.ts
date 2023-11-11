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

  getToken(): string | null {
    return this.storageService.load<string>(StorageKey.TOKEN);
  }

  getCurrentUser(): Observable<User | null> {
    const token: string | null = this.getToken();
    if (token) {
      const encodedPayload: string = token.split(".")[1];
      const payload: string = window.atob(encodedPayload);
      return of(JSON.parse(payload));
    }
    return of(null);
  }

  isLogged(): boolean {
    return !this.getToken();
  }

  doLogin(data: Token): void {
    this.storageService.store<string>(StorageKey.TOKEN, data.compactToken);
  }

  doLogout(): void {
    localStorage.removeItem(StorageKey.TOKEN);
  }
}