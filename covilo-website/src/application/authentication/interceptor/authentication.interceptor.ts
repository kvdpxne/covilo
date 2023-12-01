import {ClassProvider, Inject, Injectable} from "@angular/core";
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {catchError, Observable, of, switchMap, tap, throwError} from "rxjs";
import {Router} from "@angular/router";
import {UserAuthenticationService} from "../../core";
import {TokenAuthenticationStrategy} from "../service/token-authentication.strategy";
import {AUTHENTICATION_STRATEGY} from "../service/authentication.strategy";
import {Token} from "../../core";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private readonly router: Router;
  private readonly authenticationService: UserAuthenticationService;
  private readonly tokenAuthenticationStrategy: TokenAuthenticationStrategy;

  /**
   * Determines whether the access token is currently being refreshed. By
   * default, this field is set to false.
   */
  private isRefreshed: boolean;

  public constructor(
    router: Router,
    authenticationService: UserAuthenticationService,
    @Inject(AUTHENTICATION_STRATEGY) tokenAuthenticationStrategy: TokenAuthenticationStrategy
  ) {
    this.router = router;
    this.authenticationService = authenticationService;
    this.tokenAuthenticationStrategy = tokenAuthenticationStrategy;
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
    this.authenticationService.logout().subscribe((): void => {
      this.router.navigateByUrl("/").catch(error => throwError(() => error));
    });
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
    if (!this.tokenAuthenticationStrategy.isLogged()) {
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
        if (403 === error.status) {
          this.handleForbiddenError();
        }
        return throwError(() => error);
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
    const token: Token | null = this.tokenAuthenticationStrategy.getToken();
    if (!token) {
      return next.handle(request);
    }
    let authenticatedRequest: HttpRequest<any> = this.authenticateRequest(
      request,
      token.compactAccessToken
    );
    return next.handle(authenticatedRequest).pipe(
      catchError((error: HttpErrorResponse): Observable<any> => {
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

export const AUTHENTICATION_INTERCEPTOR_PROVIDER: ClassProvider = {
  provide: HTTP_INTERCEPTORS,
  multi: true,
  useClass: AuthenticationInterceptor
};
