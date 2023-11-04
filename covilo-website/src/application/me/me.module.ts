import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MeComponent} from "./me.component";
import {MeRoutingModule} from "./me-routing.module";
import {ChangeAvatarComponent, ChangePasswordComponent, OverviewComponent} from "./router";
import {SideNavigationBarComponent, SideNavigationComponent} from "./component";
import {TranslateModule} from "@ngx-translate/core";
import {SharedModule} from "../shared";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations: [
    SideNavigationComponent,
    SideNavigationBarComponent,

    ChangeAvatarComponent,
    ChangePasswordComponent,
    OverviewComponent,

    MeComponent
  ],
  exports: [],
  imports: [
    CommonModule,

    TranslateModule.forChild(),

    MeRoutingModule,
    SharedModule,
    NgbTooltip
  ]
})
export class MeModule {
}
