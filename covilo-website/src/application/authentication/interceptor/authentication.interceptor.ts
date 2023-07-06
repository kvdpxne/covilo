import {ClassProvider, Inject, Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";
import {TokenAuthenticationStrategy} from "../service/token-authentication-strategy";
import {AUTHENTICATION_STRATEGY} from "../service/authentication-strategy";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private readonly router: Router;
  private readonly authenticationService: AuthenticationService;
  private readonly tokenAuthenticationStrategy: TokenAuthenticationStrategy;
  private isRefreshed: boolean;

  constructor(
    router: Router,
    authenticationService: AuthenticationService,
    @Inject(AUTHENTICATION_STRATEGY) tokenAuthenticationStrategy: TokenAuthenticationStrategy
  ) {
    this.router = router;
    this.authenticationService = authenticationService;
    this.tokenAuthenticationStrategy = tokenAuthenticationStrategy;
    this.isRefreshed = false;
  }

  private handleForbiddenError(): void {
    this.authenticationService.logout().subscribe((): void => {
      this.tokenAuthenticationStrategy.doLogout();
      this.router.navigateByUrl("/").catch(error => throwError(() => error))
    })
  }

  private handleUnauthorizedError(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isRefreshed) {
      return next.handle(request);
    }
    this.isRefreshed = true;
    if (this.tokenAuthenticationStrategy.isLogged()) {
      return next.handle(request);
    }
    return this.authenticationService.refreshToken().pipe(
      switchMap(() => {
        this.isRefreshed = false
        return next.handle(request)
      }),
      catchError(error => {
        this.isRefreshed = false
        if (403 === error.status) {
          this.handleForbiddenError();
        }
        return throwError(() => error)
      })
    );
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authenticationRequest: HttpRequest<any> = request.clone({
      withCredentials: true
    });
    return next.handle(authenticationRequest).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse && 401 === error.status) {
          return this.handleUnauthorizedError(authenticationRequest, next);
        }
        return throwError(() => error);
      })
    )
  }
}

export const AUTHENTICATION_INTERCEPTOR_PROVIDER: ClassProvider = {
  provide: HTTP_INTERCEPTORS,
  multi: true,
  useClass: AuthenticationInterceptor
};
