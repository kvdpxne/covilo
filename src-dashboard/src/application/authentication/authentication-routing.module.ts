import {RouterModule, RouterOutlet} from "@angular/router";
import {ForgotPasswordComponent, LoginComponent} from "./routers";
import {NgModule} from "@angular/core";
import {AuthenticationComponent} from "./authentication.component";
import {authenticationGuard} from "./guards/authentication.guard";

@NgModule({
  imports: [
    RouterModule.forChild([{
      path: "authentication",
      component: AuthenticationComponent,
      canActivate: [
        authenticationGuard
      ],
      children: [{
        path: "",
        redirectTo: "login",
        pathMatch: "full"
      }, {
        path: "forgot-password",
        component: ForgotPasswordComponent
      }, {
        path: "login",
        component: LoginComponent
      }]
    }])
  ],
  exports: [
    RouterOutlet
  ]
})
export class AuthenticationRoutingModule {}