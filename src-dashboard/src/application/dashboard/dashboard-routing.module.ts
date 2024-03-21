import {NgModule} from "@angular/core";
import {RouterModule, RouterOutlet} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {authenticationGuard} from "../authentication";
import {CHILDREN_ROUTES} from "./routes/routes-tree";

@NgModule({
  imports: [
    RouterModule.forChild([{
      path: "dashboard",
      component: DashboardComponent,
      canActivate: [
        authenticationGuard
      ],
      children: [
        ...CHILDREN_ROUTES
      ]
    }])
  ],
  exports: [
    RouterOutlet
  ]
})
export class DashboardRoutingModule {}