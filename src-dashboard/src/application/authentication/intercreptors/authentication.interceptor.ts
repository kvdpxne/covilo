import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationService} from "../services/authentication.service";
import {
  AuthenticationTokenStrategy
} from "../services/authentication-token-strategy";
import {Token} from "../../core";

export class AuthenticationInterceptor
  implements HttpInterceptor {

  /**
   * Determines whether the access token is currently being refreshed. By
   * default, this field is set to false.
   */
  private isRefreshed: boolean = false;

  public constructor(
    private readonly authenticationService: AuthenticationService,
    private readonly authenticationTokenStrategy: AuthenticationTokenStrategy
  ) {
  }

  /**
   * Identifies and handles a given HTTP request.
   */
  public intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token: Token | null = this.authenticationTokenStrategy.getToken();
    if (!token) {
      return next.handle(request);
    }

    let accessRequest: HttpRequest<any> = request.clone({
      setHeaders: {
        Authorization: token.compactAccessToken
      }
    });

    return next.handle(accessRequest);
  }
}
