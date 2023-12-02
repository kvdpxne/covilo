import {Inject, Injectable} from "@angular/core";
import {catchError, EMPTY, Observable, of, tap} from "rxjs";

import {ApiHttpClientService} from "../../shared";

import {LoginRequest, SignupRequest, Token, User, UserLifecycleService, UserService} from "../index";
import {AUTHENTICATION_STRATEGY, AuthenticationStrategy} from "../../authentication/service/authentication.strategy";
import {TokenAuthenticationStrategy} from "../../authentication/service/token-authentication.strategy";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class UserAuthenticationService {

  private readonly httpClientService: ApiHttpClientService;
  private readonly authenticationStrategy: AuthenticationStrategy<any>;

  private readonly userService: UserService;
  private readonly userLifecycleService: UserLifecycleService;

  constructor(
    httpClientService: ApiHttpClientService,
    @Inject(AUTHENTICATION_STRATEGY) authenticationStrategy: TokenAuthenticationStrategy,
    userService: UserService,
    userLifecycleService: UserLifecycleService
  ) {
    this.httpClientService = httpClientService;
    this.authenticationStrategy = authenticationStrategy;
    this.userService = userService;
    this.userLifecycleService = userLifecycleService;
  }

  /**
   * Sends a request to send non-sensitive user data and then saves it to
   * temporary memory to avoid repeated requests.
   */
  public cacheMe(): void {
    this.userService.me.subscribe((user: User): void => {
      this.userLifecycleService.addUser(user);
    });
  }

  /**
   * Sends a user registration request.
   */
  public signup(request: SignupRequest): Observable<Token> {
    const path: string = "auth/register";
    return this.httpClientService.post<Token>(path, request).pipe(
      tap((token: Token): void => {
        this.authenticationStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  /**
   * Sends a user login request.
   */
  public login(request: LoginRequest): Observable<Token> {
    const path: string = "auth/login";
    return this.httpClientService.post<Token>(path, request).pipe(
      tap((token: Token): void => {
        this.authenticationStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  public isLogged(): Observable<boolean> {
    return of(this.authenticationStrategy.isLogged());
  }

  /**
   * Sends a request to refresh the user's authentication token.
   */
  public refreshToken(): Observable<Token> {
    const path: string = "auth/refresh-token";

    return this.httpClientService.post<Token>(path).pipe(
      tap((token: Token): void => {
        this.authenticationStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  public logout(refresh: boolean = false): Observable<never> {
    const path: string = "auth/logout";
    return this.httpClientService.post<never>(path).pipe(
      tap((): void => {
        this.authenticationStrategy.doLogout();
        this.userLifecycleService.removeUser();

        if (refresh) {
          location.reload();
        }
      })
    );
  }
}
