import { NgModule } from "@angular/core"
import { FormsModule } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { BrowserModule } from "@angular/platform-browser"
import {
  NgbAlertModule,
  NgbCollapseModule,
  NgbDropdownModule
} from "@ng-bootstrap/ng-bootstrap"
import { TranslateModule } from "@ngx-translate/core"

//
import { SharedModule } from "src/application/shared"

//
import {
  AvatarComponent,
  FooterComponent,
  NavigationComponent,
  NotificationComponent
} from "./components"

@NgModule({
  declarations: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent,
    AvatarComponent
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
    NgbDropdownModule
  ],
  exports: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent
  ]
})
export class CoreModule {
}