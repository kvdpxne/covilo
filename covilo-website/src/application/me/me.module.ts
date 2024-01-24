import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MeComponent} from "./me.component";
import {MeRoutingModule} from "./me-routing.module";
import {ChangeAvatarComponent, SecurityComponent, OverviewComponent} from "./router";
import {SideNavigationBarComponent, SideNavigationComponent} from "./component";
import {TranslateModule} from "@ngx-translate/core";
import {SharedModule} from "../shared";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import {TextFieldComponent} from "../authentication/component/text-field/text-field.component";
import {SideNavigationItemComponent} from "./component/side-navigation-item/side-navigation-item.component";

@NgModule({
  declarations: [
    SideNavigationComponent,
    SideNavigationBarComponent,

    ChangeAvatarComponent,
    SecurityComponent,
    OverviewComponent,

    MeComponent
  ],
  exports: [],
  imports: [
    CommonModule,

    TranslateModule.forChild(),

    MeRoutingModule,
    SharedModule,
    NgbTooltip,
    FormsModule,
    TextFieldComponent,
    SideNavigationItemComponent
  ]
})
export class MeModule {
}
