import { NgModule } from "@angular/core"
import { FormsModule } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { BrowserModule } from "@angular/platform-browser"
import {
  NgbAlertModule,
  NgbCollapseModule,
  NgbDropdownModule, NgbPopover
} from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core"

//
import { SharedModule } from "src/application/shared"

//
import {
  AvatarComponent,
  FooterComponent,
  NavigationComponent,
  NotificationComponent
} from "./component";
import { LanguageSwitchComponent } from "./component"

@NgModule({
  declarations: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent,
    AvatarComponent,
    LanguageSwitchComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    FormsModule,
    NgbCollapseModule,
    TranslateModule.forChild(),
    SharedModule,
    NgbAlertModule,
    NgbDropdownModule,
    NgbPopover
  ],
  exports: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent
  ]
})
export class CoreModule {
}