import {Injectable} from "@angular/core";
import {Observable, of, tap} from "rxjs";

import {ApiHttpClientService} from "../../shared";

import {LoginRequest, SignupRequest, Token, User, UserLifecycleService, UserService} from "../../core";
import {AuthenticationTokenStrategy} from "./authentication-token-strategy";


/**
 *
 */
const ROOT_PATH: string = "authentication";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  constructor(
    private readonly httpClientService: ApiHttpClientService,
    private readonly authenticationTokenStrategy: AuthenticationTokenStrategy,
    private readonly userService: UserService,
    private readonly userMeService: UserLifecycleService
  ) {

  }

  /**
   * Sends a request to send non-sensitive user data and then saves it to
   * temporary memory to avoid repeated requests.
   */
  public cacheMe(): void {
    this.userService.me.subscribe((user: User): void => {
      this.userMeService.addUser(user);
    });
  }

  /**
   * Sends a user login request.
   */
  public login(request: LoginRequest): Observable<Token> {
    const path: string = "authentication/login";
    return this.httpClientService.post<Token>(path, request).pipe(
      tap((token: Token): void => {
        this.authenticationTokenStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  public isLogged(): Observable<boolean> {
    return of(this.authenticationTokenStrategy.isLogged());
  }

  /**
   * Sends a request to refresh the user's authentication token.
   */
  public refreshToken(): Observable<Token> {
    const path: string = "authentication/refresh-token";

    return this.httpClientService.post<Token>(path).pipe(
      tap((token: Token): void => {
        this.authenticationTokenStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  /**
   * @param refresh Specifies whether the page should be reloaded after a
   *                logout request is sent, by default, the page is not
   *                reloaded after sending the request.
   */
  public logout(
    refresh: boolean = false
  ): void {
    // Deletes the currently authenticated user's data from the browser's
    // temporary memory and other user-related data stored in the browser
    // necessary for the operation of some features.
    this.authenticationTokenStrategy.doLogout();
    this.userMeService.removeUser();

    if (refresh) {
      location.reload();
    }
  }
}
