import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MeComponent} from "./me.component";
import {meGuard} from "./guard/me.guard";
import {ChangeAvatarComponent, ChangePasswordComponent, OverviewComponent} from "./router";

const routes: Routes = [{
  path: "me",
  component: MeComponent,
  canActivate: [
    meGuard
  ],
  children: [{
    path: "",
    redirectTo: "overview",
    pathMatch: "full"
  }, {
    path: "change-avatar",
    component: ChangeAvatarComponent
  }, {
    path: "change-password",
    component: ChangePasswordComponent
  }, {
    path: "overview",
    component: OverviewComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MeRoutingModule {}