import { NgModule } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule, ReactiveFormsModule } from "@angular/forms"
import { TranslateModule } from "@ngx-translate/core"

//
import { ApplicationRoutingModule } from "./authentication-routing.module"

// Root our application authentication component
import { AuthenticationComponent } from "./authentication.component"

//
import {
  ForgotPasswordComponent,
  LoginComponent,
  LogoutComponent
} from "./routers"

import {
  ConfirmButtonComponent,
  EmailInputComponent,
  PasswordInputComponent
} from "./components";
import { SignupComponent } from "./routers"

@NgModule({
  providers: [],
  declarations: [
    AuthenticationComponent,
    ForgotPasswordComponent,
    LoginComponent,
    LogoutComponent,
    ConfirmButtonComponent,
    PasswordInputComponent,
    EmailInputComponent,
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
