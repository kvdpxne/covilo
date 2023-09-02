import {NgModule} from "@angular/core"
import {RouterModule, Routes} from "@angular/router"
import {MeComponent} from "./me.component";
import {meGuard} from "./guard/me.guard";

const routes: Routes = [{
  path: "me",
  canActivate: [
    meGuard
  ],
  component: MeComponent,
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MeRoutingModule { }