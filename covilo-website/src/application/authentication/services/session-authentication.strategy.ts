import {AuthenticationStrategy} from "./authentication.strategy";
import {Observable, of} from "rxjs";
import {User} from "../../core";

export class SessionAuthenticationStrategy
  implements AuthenticationStrategy<any> {

  getCurrentUser(): Observable<User> {
    return of();
  }

  isLogged(): boolean {
    return false;
  }

  doLogin(data: any): void {
  }

  doLogout(): void {
  }
}