import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"

import {
  AuthenticationComponent,
  ForgotPasswordComponent,
  LoginComponent,
  LogoutComponent
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
