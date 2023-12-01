import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {AuthenticationRoutingModule} from "./authentication-routing.module";
import {SharedModule} from "../shared";
import {AUTHENTICATION_STRATEGY_PROVIDER} from "./service/authentication.strategy";
import {AUTHENTICATION_INTERCEPTOR_PROVIDER} from "./interceptor/authentication.interceptor";
import {LoginComponent, ResetPasswordComponent, SignupComponent} from "./router";
import {NavigationBarComponent, SideBarComponent} from "./component";
import {AuthenticationComponent} from "./authentication.component";
import {TextFieldComponent} from "./component/text-field/text-field.component";

@NgModule({
  providers: [
    AUTHENTICATION_STRATEGY_PROVIDER,
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  declarations: [
    NavigationBarComponent,
    SideBarComponent,
    LoginComponent,
    ResetPasswordComponent,
    SignupComponent,
    AuthenticationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    SharedModule,
    AuthenticationRoutingModule,
    TextFieldComponent
  ]
})
export class AuthenticationModule {
}
