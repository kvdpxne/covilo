import {AuthenticationStrategy} from "./authentication-strategy";
import {Token} from "../payload/token";
import {Observable, of} from "rxjs";
import {User} from "../../core";

export class TokenAuthenticationStrategy implements AuthenticationStrategy<Token> {

  private readonly TOKEN: string = "token";

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN);
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
    localStorage.setItem(this.TOKEN, data.accessToken);
  }

  doLogout(): void {
    localStorage.removeItem(this.TOKEN);
  }
}