import {Inject, Injectable} from "@angular/core"
import {catchError, Observable, of, tap} from "rxjs"

import {ApiHttpClientService} from "../../"

import {LoginCredentials} from "../"
import {Token} from "../payload/token";
import {AUTHENTICATION_STRATEGY, AuthenticationStrategy} from "./authentication-strategy";
import {TokenAuthenticationStrategy} from "./token-authentication-strategy";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  private readonly authenticationStrategy: AuthenticationStrategy<any>

  private readonly baseUrl: string

  constructor(private readonly httpClient: ApiHttpClientService,
              @Inject(AUTHENTICATION_STRATEGY) authenticationStrategy: TokenAuthenticationStrategy
  ) {
    const apiUrl = httpClient.getUrl()
    this.baseUrl = apiUrl + "authentication"
    this.authenticationStrategy = authenticationStrategy;
  }

  login2(credentials: LoginCredentials): Observable<Token> {
    const url: string = "auth/login";
    return this.httpClient.post<Token>(url, credentials)
      .pipe(
        tap((token: Token) => this.authenticationStrategy.doLogin(token))
      );
  }

  isLoggedIn(): Observable<boolean> {
    return this.authenticationStrategy.getCurrentUser().pipe(
      map(user => !!user),
      catchError(() => of(false))
    );
  }

  refreshToken(): Observable<void> {
    return this.httpClient.post("auth/refresh-token", null);
  }

  logout(): Observable<void> {
    return this.httpClient.post("auth/logout", null);
  }
}
