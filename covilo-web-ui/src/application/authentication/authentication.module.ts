import { NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule, ReactiveFormsModule } from "@angular/forms"
import { TranslateModule } from "@ngx-translate/core"

import { ApplicationRoutingModule } from "./authentication-routing.module"

//
import {
  AuthenticationComponent,
  ForgotPasswordComponent,
  AUTHENTICATION_INTERCEPTOR_PROVIDER,
  LoginComponent,
  LogoutComponent,
  SignupComponent
} from "./"

@NgModule({
  providers: [
    AUTHENTICATION_INTERCEPTOR_PROVIDER
  ],
  declarations: [
    AuthenticationComponent,
    ForgotPasswordComponent,
    LoginComponent,
    LogoutComponent,
    SignupComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    ApplicationRoutingModule
  ]
})
export class AuthenticationModule {
}
