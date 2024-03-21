import {Routes} from "@angular/router";
import {WelcomeComponent} from "./welcome/welcome.component";

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
}];