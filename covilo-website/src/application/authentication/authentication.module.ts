import {NgModule} from "@angular/core";
import {AUTHENTICATION_STRATEGY_PROVIDER} from "./services/authentication.strategy";
import {AUTHENTICATION_INTERCEPTOR_PROVIDER} from "./interceptors/authentication.interceptor";
import {AuthenticationRoutingModule} from "./authentication-routing.module";

@NgModule({
  providers: [
    AUTHENTICATION_STRATEGY_PROVIDER,
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  imports: [
    AuthenticationRoutingModule
  ]
})
export class AuthenticationModule {
}
