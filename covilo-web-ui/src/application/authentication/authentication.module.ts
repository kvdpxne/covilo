import {NgModule} from "@angular/core"
import {CommonModule} from "@angular/common"
import {FormsModule, ReactiveFormsModule} from "@angular/forms"
import {TranslateModule} from "@ngx-translate/core"
import {ApplicationRoutingModule} from "./authentication-routing.module"
import {AuthenticationComponent, ForgotPasswordComponent, LoginComponent} from "./"
import {SharedModule} from "../shared"
import {AUTHENTICATION_STRATEGY_PROVIDER} from "./service/authentication-strategy";
import {AUTHENTICATION_INTERCEPTOR_PROVIDER} from "./interceptor/authentication.interceptor";

@NgModule({
  providers: [
    AUTHENTICATION_STRATEGY_PROVIDER,
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  declarations: [
    AuthenticationComponent,
    ForgotPasswordComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    ApplicationRoutingModule,
    SharedModule
  ]
})
export class AuthenticationModule {
}
