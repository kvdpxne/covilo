import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"

import {
  AuthenticationComponent,
  ForgotPasswordComponent,
  LoginComponent,
  LogoutComponent,
  SignupComponent
} from "./"

const routes: Routes = [
  {
    path: "authentication",
    component: AuthenticationComponent,
    children: [
      {
        path: "forgot-password",
        component: ForgotPasswordComponent
      },
      {
        path: "login",
        component: LoginComponent
      },
      {
        path: "logout",
        component: LogoutComponent
      },
      {
        path: "signup",
        component: SignupComponent
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {
}
