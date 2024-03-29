import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MeComponent} from "./me.component";
import {meGuard} from "./guards/me.guard";
import {ChangeAvatarComponent, OverviewComponent, RouteName, SecurityComponent} from "./routers";
import {buildLinkArrayWithChildren, Link} from "../shared";
import {ContactComponent} from "./routers/contact/contact.component";

/**
 *
 */
export const ROUTE_LINKS: Link[] = buildLinkArrayWithChildren("/me", [
  RouteName.OVERVIEW,
  RouteName.APPEARANCE,
  RouteName.CONTACT,
  RouteName.SECURITY
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
    path: RouteName.APPEARANCE,
    component: ChangeAvatarComponent
  }, {
    path: RouteName.CONTACT,
    component: ContactComponent
  }, {
    path: RouteName.SECURITY,
    component: SecurityComponent
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