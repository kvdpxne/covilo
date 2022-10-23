import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"

//
import { AuthenticationComponent } from "./authentication.component"

//
import {
  ForgotPasswordComponent,
  LoginComponent,
  LogoutComponent
} from "./routers"

//
const routes: Routes = [
  {
    pathMatch: "full",
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
