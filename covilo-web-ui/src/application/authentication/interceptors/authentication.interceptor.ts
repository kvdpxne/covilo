import { ClassProvider, Injectable } from "@angular/core"
import { Router } from "@angular/router"
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http"
import { catchError, Observable, switchMap, throwError } from "rxjs"
import { AuthenticationService, StorageService } from "../"

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private isRefreshed: boolean = false

  constructor(
    private readonly router: Router,
    private readonly authenticationService: AuthenticationService,
    private readonly storageService: StorageService
  ) {
  }

  // 403
  private handleForbiddenError() {
    const email = this.storageService.getUser()!!.email
    this.authenticationService.logout(email).subscribe(() => {
      this.storageService.clean()
      this.router.navigateByUrl("/").catch(error => throwError(() => error))
    })
  }

  // 401
  private handleUnauthorizedError(
    request: HttpRequest<any>,
    next: HttpHandler
  ) {
    if (this.isRefreshed) {
      return next.handle(request)
    }
    this.isRefreshed = true
    if (!this.storageService.isLogged()) {
      return next.handle(request)
    }
    return this.authenticationService.refresh().pipe(
      switchMap(() => {
        this.isRefreshed = false
        return next.handle(request)
      }),
      catchError(error => {
        this.isRefreshed = false
        if (403 === error.status) {
          this.handleForbiddenError()
        }
        return throwError(() => error)
      })
    )
  }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authenticationRequest = request.clone({
      withCredentials: true
    })
    return next.handle(authenticationRequest).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse && 401 === error.status) {
          return this.handleUnauthorizedError(authenticationRequest, next)
        }
        return throwError(() => error)
      })
    )
  }
}

export const AUTHENTICATION_INTERCEPTOR_PROVIDER: ClassProvider = {
  provide: HTTP_INTERCEPTORS,
  multi: true,
  useClass: AuthenticationInterceptor
}
