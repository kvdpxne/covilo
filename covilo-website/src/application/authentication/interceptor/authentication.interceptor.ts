import {ClassProvider, Inject, Injectable} from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HTTP_INTERCEPTORS
} from "@angular/common/http";
import {catchError, Observable, of, switchMap, throwError} from "rxjs";
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";
import {TokenAuthenticationStrategy} from "../service/token-authentication-strategy";
import {AUTHENTICATION_STRATEGY} from "../service/authentication-strategy";
import {Token} from "../payload/token";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private readonly router: Router;
  private readonly authenticationService: AuthenticationService;
  private readonly tokenAuthenticationStrategy: TokenAuthenticationStrategy;

  /**
   * Determines whether the access token is currently being refreshed. By
   * default, this field is set to false.
   */
  private isRefreshed: boolean;

  public constructor(
    router: Router,
    authenticationService: AuthenticationService,
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
  private authenticateRequest(request: HttpRequest<any>, accessToken: string): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${accessToken}`
      }
    });
  }

  private handleForbiddenError(): void {
    this.authenticationService.logout().subscribe((): void => {
      this.tokenAuthenticationStrategy.doLogout();
      this.router.navigateByUrl("/").catch(error => throwError(() => error));
    });
  }

  private handleUnauthorizedError(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isRefreshed) {
      return next.handle(request);
    }
    this.isRefreshed = true;
    /* Checking if the token is assigned in the browser's storage, if not then
     * sending a request to refresh the access token should never be successful
     * because the old, expired token is needed for the user to get a new
     * access token.
     */
    if (this.tokenAuthenticationStrategy.isLogged()) {
      return next.handle(request);
    }
    return this.authenticationService.refreshToken().pipe(
      switchMap((token: Token) => {
        return next.handle(this.authenticateRequest(request, token.accessToken));
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
  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string | null = this.tokenAuthenticationStrategy.getToken();
    if (!token) {
      return next.handle(request);
    }
    const authenticatedRequest: HttpRequest<any> = this.authenticateRequest(request, token);
    return next.handle(authenticatedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if (401 === error.status) {
          return this.handleUnauthorizedError(authenticatedRequest, next);
        }
        if (403 === error.status) {
          this.handleForbiddenError();
          return of()
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
