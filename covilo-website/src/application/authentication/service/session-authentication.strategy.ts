import {AuthenticationStrategy} from "./authentication.strategy";
import {Observable, of} from "rxjs";
import {User} from "../../core";

export class SessionAuthenticationStrategy
  implements AuthenticationStrategy<any>{
  doLogin(data: any): void {
  }

  doLogout(): void {
  }

  getCurrentUser(): Observable<User> {
    return of();
  }

  isLogged(): boolean {
    return false;
  }

}