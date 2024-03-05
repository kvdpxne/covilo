import {NgModule} from "@angular/core";
import {RouterModule, RouterOutlet} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {WelcomeComponent} from "./routes";
import {authenticationGuard} from "../authentication";

@NgModule({
  imports: [
    RouterModule.forChild([{
      path: "dashboard",
      component: DashboardComponent,
      canActivate: [
        authenticationGuard
      ],
      children: [{
        path: "",
        redirectTo: "welcome",
        pathMatch: "full"
      }, {
        path: "welcome",
        component: WelcomeComponent
      }]
    }])
  ],
  exports: [
    RouterOutlet
  ]
})
export class DashboardRoutingModule {}