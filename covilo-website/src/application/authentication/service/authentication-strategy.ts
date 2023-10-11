import {Observable} from "rxjs";
import {User} from "../../core";
import {FactoryProvider, inject, InjectionToken} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TokenAuthenticationStrategy} from "./token-authentication-strategy";
import {StorageService} from "../../shared";

export interface AuthenticationStrategy<T> {

  getCurrentUser(): Observable<User | null>;

  doLogin(data: T): void;

  doLogout(): void;
}

export const AUTHENTICATION_STRATEGY: InjectionToken<AuthenticationStrategy<any>> = new InjectionToken(
  "AuthenticationStrategy"
);

export const AUTHENTICATION_STRATEGY_PROVIDER: FactoryProvider = {
  provide: AUTHENTICATION_STRATEGY,
  deps: [HttpClient],
  useFactory: () => new TokenAuthenticationStrategy(
    inject(StorageService)
  )
};