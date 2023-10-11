import {NgModule} from "@angular/core"
import {CommonModule} from "@angular/common"
import {FormsModule, ReactiveFormsModule} from "@angular/forms"
import {TranslateModule} from "@ngx-translate/core"
import {AuthenticationRoutingModule} from "./authentication-routing.module"
import {AuthenticationComponent, ResetPasswordComponent, LoginComponent} from "./"
import {SharedModule} from "../shared"
import {AUTHENTICATION_STRATEGY_PROVIDER} from "./service/authentication-strategy";
import {AUTHENTICATION_INTERCEPTOR_PROVIDER} from "./interceptor/authentication.interceptor";
import {SignupComponent} from './router';

@NgModule({
  providers: [
    AUTHENTICATION_STRATEGY_PROVIDER,
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  declarations: [
    AuthenticationComponent,
    ResetPasswordComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    SharedModule,
    AuthenticationRoutingModule,
  ]
})
export class AuthenticationModule {
}
