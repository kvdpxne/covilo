import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MeComponent} from "./me.component";
import {MeRoutingModule} from "./me-routing.module";
import {ChangeAvatarComponent, OverviewComponent} from "./router";
import {SideNavigationBarComponent, SideNavigationComponent} from "./component";
import {SharedModule} from "../shared";
import { ChangePasswordComponent } from "./router";

@NgModule({
  declarations: [
    MeComponent,
    OverviewComponent,
    SideNavigationBarComponent,
    SideNavigationComponent,
    ChangeAvatarComponent,
    ChangePasswordComponent
  ],
  exports: [
  ],
  imports: [
    CommonModule,
    MeRoutingModule,

    SharedModule
  ]
})
export class MeModule {
}
