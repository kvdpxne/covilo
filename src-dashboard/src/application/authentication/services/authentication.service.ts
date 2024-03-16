import {Injectable} from "@angular/core";
import {Observable, tap} from "rxjs";

import {
  ApiHttpClientService,
  InMemoryStorage,
  StorageKey
} from "../../shared";

import {LoginRequest, Token, UserMeService} from "../../core";
import {TokenAuthenticationStrategy} from "./token-authentication-strategy";
import {AuthenticationStrategy} from "./authentication-strategy";

/**
 * Default path for authentication-related API endpoints.
 *
 * This constant defines the default base URL path used for sending requests
 * pertaining to user authentication operations, including login, logout,
 * and token validation. It serves as the starting point for constructing
 * endpoint URLs for authentication-related tasks within the application's
 * backend services.
 *
 * Developers can utilize this constant as the foundation for forming specific
 * endpoint URLs by appending additional path segments to it. For example,
 * appending "/login" would result in the endpoint URL for the login operation,
 * and appending "/logout" would form the URL for the logout functionality.
 *
 * It is crucial to maintain consistency with this default path across the
 * application to ensure seamless communication with the backend authentication
 * services. Any modifications to this path should be carefully considered
 * and thoroughly tested to avoid disruptions in authentication functionality.
 */
const DEFAULT_PATH: string = "api/v1/authentication";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {

  private readonly apiHttpClientService: ApiHttpClientService;

  private readonly authenticationStrategy: AuthenticationStrategy<Token>;

  private readonly userMeService: UserMeService;

  private readonly inMemoryStorage: InMemoryStorage;

  public constructor(
    apiHttpClientService: ApiHttpClientService,
    authenticationTokenStrategy: TokenAuthenticationStrategy,
    userMeService: UserMeService,
    inMemoryStorage: InMemoryStorage
  ) {
    this.apiHttpClientService = apiHttpClientService;
    this.authenticationStrategy = authenticationTokenStrategy;
    this.userMeService = userMeService;
    this.inMemoryStorage = inMemoryStorage;
  }

  /**
   * Sends a request to send non-sensitive user data and then saves it to
   * temporary memory to avoid repeated requests.
   */
  public cacheMe(): void {
    this.userMeService.me().subscribe(user => {
      this.inMemoryStorage.store(StorageKey.AUTHENTICATED_USER, user);
    });
  }

  /**
   * Sends a user login request.
   */
  public login(
    request: LoginRequest
  ): Observable<Token> {
    const path: string = `${DEFAULT_PATH}/login`;
    return this.apiHttpClientService.post<Token>(path, request).pipe(
      tap(token => {
        this.authenticationStrategy.doLogin(token);
        this.cacheMe();
      })
    );
  }

  /**
   * Sends a request to refresh the user's authentication token.
   */
  public refreshToken(): Observable<Token> {
    const path: string = `${DEFAULT_PATH}/refresh-token`;

    return this.apiHttpClientService.post<Token>(path).pipe(
      tap((token: Token): void => {
        this.authenticationStrategy.doLogin(token);
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
    this.authenticationStrategy.doLogout();
    this.inMemoryStorage.remove(StorageKey.TOKEN);

    if (refresh) {
      location.reload();
    }
  }
}
