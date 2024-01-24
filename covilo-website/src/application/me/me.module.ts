import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MeComponent} from "./me.component";
import {MeRoutingModule} from "./me-routing.module";
import {ChangeAvatarComponent, OverviewComponent, SecurityComponent} from "./router";
import {SideNavigationBarComponent, SideNavigationComponent, SideNavigationItemComponent} from "./component";
import {TranslateModule} from "@ngx-translate/core";
import {SharedModule} from "../shared";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import {TextFieldComponent} from "../authentication/component/text-field/text-field.component";

@NgModule({
  imports: [
    CommonModule,
    TranslateModule.forChild(),
    MeRoutingModule,
    SharedModule,
    NgbTooltip,
    FormsModule,
    TextFieldComponent,
    SideNavigationItemComponent,
    SideNavigationComponent,
    SideNavigationBarComponent,
    ChangeAvatarComponent,
    SecurityComponent,
    OverviewComponent,
    MeComponent
  ]
})
export class MeModule {}
