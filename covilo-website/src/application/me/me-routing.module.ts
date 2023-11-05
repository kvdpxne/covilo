import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MeComponent} from "./me.component";
import {meGuard} from "./guard/me.guard";
import {ChangeAvatarComponent, ChangePasswordComponent, OverviewComponent, RouteName} from "./router";
import {buildLinkArrayWithChildren, Link} from "../shared";

/**
 *
 */
export const ROUTE_LINKS: Link[] = buildLinkArrayWithChildren("/me", [
  RouteName.OVERVIEW,
  RouteName.CHANGE_PASSWORD,
  RouteName.CHANGE_AVATAR,
]);

/**
 *
 */
const ROUTES: Routes = [{
  path: "me",
  component: MeComponent,
  canActivate: [
    meGuard
  ],
  children: [{
    path: "",
    redirectTo: RouteName.OVERVIEW,
    pathMatch: "full"
  }, {
    path: RouteName.CHANGE_AVATAR,
    component: ChangeAvatarComponent
  }, {
    path: RouteName.CHANGE_PASSWORD,
    component: ChangePasswordComponent
  }, {
    path: RouteName.OVERVIEW,
    component: OverviewComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forChild(ROUTES)],
  exports: [RouterModule]
})
export class MeRoutingModule {}