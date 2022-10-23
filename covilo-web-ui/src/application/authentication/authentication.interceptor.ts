import { Injectable } from "@angular/core"
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http"
import { Observable } from "rxjs"
import { AuthenticationService } from "./authentication.service"

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private readonly service: AuthenticationService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
  }
}
