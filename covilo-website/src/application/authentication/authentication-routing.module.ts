import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationComponent, ResetPasswordComponent, LoginComponent, SignupComponent} from "./";
import {authenticationGuard} from "./guards/authentication.guard";

const routes: Routes = [{
  path: "authentication",
  component: AuthenticationComponent,
  canActivate: [
    // An authentication guard that prevents a logged-in user from accessing
    // an authentication-related component.
    authenticationGuard
  ],
  children: [{
    path: "",
    redirectTo: "login",
    pathMatch: "full"
  }, {
    path: "reset-password",
    component: ResetPasswordComponent
  }, {
    path: "login",
    component: LoginComponent
  }, {
    path: "signup",
    component: SignupComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule {}
