import {RouterModule, RouterOutlet} from "@angular/router";
import {ForgotPasswordComponent, LoginComponent} from "./routers";
import {NgModule} from "@angular/core";
import {AuthenticationComponent} from "./authentication.component";

@NgModule({
  imports: [
    RouterModule.forChild([{
      path: "authentication",
      component: AuthenticationComponent,
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