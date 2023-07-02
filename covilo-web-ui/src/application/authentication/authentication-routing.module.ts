import {NgModule} from "@angular/core"
import {RouterModule, Routes} from "@angular/router"
import {AuthenticationComponent, ForgotPasswordComponent, LoginComponent} from "./"
import {authenticationGuard} from "./guard/authentication.guard";

const routes: Routes = [{
  path: "authentication",
  component: AuthenticationComponent,
  canActivate: [authenticationGuard],
  children: [{
    path: "forgot-password",
    component: ForgotPasswordComponent
  }, {
    path: "login",
    component: LoginComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {}
