import {Routes} from "@angular/router";
import {WelcomeComponent} from "./welcome/welcome.component";
import {
  UserManagementComponent
} from "./user-management/user-management.component";

/**
 *
 */
export const CHILDREN_ROUTES: Routes = [{
  path: "",
  redirectTo: "welcome",
  pathMatch: "full"
}, {
  path: "welcome",
  component: WelcomeComponent
}, {
  path: "user-management",
  component: UserManagementComponent
}];