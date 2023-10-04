import {Inject, Injectable} from "@angular/core";
import {catchError, Observable, of, tap} from "rxjs";

import {ApiHttpClientService, User} from "../../";

import {LoginRequest} from "../";
import {Token} from "../../core/model/token";
import {AUTHENTICATION_STRATEGY, AuthenticationStrategy} from "./authentication-strategy";
import {TokenAuthenticationStrategy} from "./token-authentication-strategy";
import {map} from "rxjs/operators";
import {SignupRequest} from "../../core/playload/signup-request";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  private readonly apiHttpClientService: ApiHttpClientService;
  private readonly authenticationStrategy: AuthenticationStrategy<any>;

  constructor(
    httpClientService: ApiHttpClientService,
    @Inject(AUTHENTICATION_STRATEGY) authenticationStrategy: TokenAuthenticationStrategy
  ) {
    this.apiHttpClientService = httpClientService;
    this.authenticationStrategy = authenticationStrategy;
  }

  public signup(request: SignupRequest): Observable<Token> {
    return this.apiHttpClientService.post<Token>("auth/register", request).pipe(
      tap((token: Token): void => this.authenticationStrategy.doLogin(token))
    );
  }

  public login(request: LoginRequest): Observable<Token> {
    return this.apiHttpClientService.post<Token>("auth/login", request).pipe(
      tap((token: Token): void => this.authenticationStrategy.doLogin(token))
    );
  }

  public isLoggedIn(): Observable<boolean> {
    return this.authenticationStrategy.getCurrentUser().pipe(
      map((user: User | null) => !!user),
      catchError(() => of(false))
    );
  }

  public refreshToken(): Observable<Token> {
    return this.apiHttpClientService.post<Token>("auth/refresh-token", {}).pipe(
      tap((token: Token): void => this.authenticationStrategy.doLogin(token))
    );
  }

  public logout(refresh: boolean = false): Observable<never> {
    return this.apiHttpClientService.post<never>("auth/logout", {}).pipe(
      tap((): void => {
        this.authenticationStrategy.doLogout();
        if (refresh) {
          location.reload();
        }
      })
    );
  }
}
