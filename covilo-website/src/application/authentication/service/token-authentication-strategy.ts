import {AuthenticationStrategy} from "./authentication-strategy";
import {Token} from "../payload/token";
import {Observable, of} from "rxjs";
import {User} from "../../core";
import {StorageService} from "../../shared/services/storage.service";
import {StorageKey} from "../../shared/services/storage-key";

export class TokenAuthenticationStrategy implements AuthenticationStrategy<Token> {

  private readonly storageService: StorageService

  constructor(storageService: StorageService) {
    this.storageService = storageService;
  }

  getToken(): string | null {
    return this.storageService.getValue(StorageKey.TOKEN);
  }

  getCurrentUser(): Observable<User | null> {
    const token: string | null = this.getToken();
    if (token) {
      const encodedPayload: string = token.split(".")[1];
      const payload: string = window.atob(encodedPayload);
      return of(JSON.parse(payload));
    }
    return of(null)
  }

  isLogged(): boolean {
    return !this.getToken();
  }

  doLogin(data: Token): void {
    this.storageService.setValue(StorageKey.TOKEN, data.accessToken);
  }

  doLogout(): void {
    localStorage.removeItem("token");
  }
}