import {Injectable} from "@angular/core";
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {
  catchError,
  EMPTY,
  Observable,
  of,
  switchMap,
  tap,
  throwError
} from "rxjs";
import {Token} from "../../core";
import {
  TokenAuthenticationStrategy
} from "../services/token-authentication-strategy";
import {AuthenticationService} from "../services/authentication.service";
import {AuthenticationStrategy} from "../services/authentication-strategy";
import {Router} from "@angular/router";

@Injectable()
export class AuthenticationInterceptor
  implements HttpInterceptor {

  private readonly authenticationService: AuthenticationService;
  private readonly authenticationStrategy: AuthenticationStrategy<Token>;

  private readonly router: Router;

  /**
   * Determines whether the access token is currently being refreshed. By
   * default, this field is set to false.
   */
  private isRefreshed: boolean;

  public constructor(
    authenticationService: AuthenticationService,
    tokenAuthenticationStrategy: TokenAuthenticationStrategy,
    router: Router
  ) {
    this.authenticationService = authenticationService;
    this.authenticationStrategy = tokenAuthenticationStrategy;
    this.router = router;
    this.isRefreshed = false;
  }

  /**
   * Signs each sending request with an access token.
   */
  private authenticateRequest(
    request: HttpRequest<any>,
    compactToken: string
  ): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${compactToken}`
      }
    });
  }

  private handleForbiddenError(): void {
    this.authenticationService.logout(true);
    this.router.navigateByUrl("authentication/login");
  }

  private handleUnauthorizedError(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.isRefreshed) {
      return next.handle(request);
    }
    this.isRefreshed = true;
    /* Checking if the token is assigned in the browser's storage, if not then
     * sending a request to refresh the access token should never be successful
     * because the old, expired token is needed for the user to get a new
     * access token.
     */
    if (!this.authenticationStrategy.isLogged()) {
      return next.handle(request);
    }
    return this.authenticationService.refreshToken().pipe(
      tap((): void => {
        this.isRefreshed = false;
      }),
      switchMap((token: Token): Observable<HttpEvent<any>> => {
        return next.handle(
          this.authenticateRequest(request, token.compactAccessToken)
        );
      }),
      catchError(error => {
        console.log(error);
        if (undefined === error.status || 403 === error.status) {
          this.handleForbiddenError();
        }
        return EMPTY;
      })
    );
  }

  /**
   * Identifies and handles a given HTTP request.
   */
  public intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //
    if (!(this.authenticationStrategy instanceof TokenAuthenticationStrategy)) {
      return next.handle(request);
    }

    const token = this.authenticationStrategy.getToken();
    if (!token) {
      return next.handle(request);
    }

    let authenticatedRequest = this.authenticateRequest(
      request,
      token.compactAccessToken
    );

    return next.handle(authenticatedRequest).pipe(
      catchError(error => {
        if (401 === error.status) {
          authenticatedRequest = this.authenticateRequest(
            request,
            token.compactRefreshToken
          );
          return this.handleUnauthorizedError(authenticatedRequest, next);
        }
        if (403 === error.status) {
          this.handleForbiddenError();
          return of();
        }
        return throwError(() => error);
      })
    );

  }
}
