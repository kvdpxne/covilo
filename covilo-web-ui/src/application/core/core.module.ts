import { NgModule } from "@angular/core"
import { FormsModule } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { BrowserModule } from "@angular/platform-browser"
import { NgbAlertModule, NgbCollapseModule } from "@ng-bootstrap/ng-bootstrap"
import { TranslateModule } from "@ngx-translate/core"

//
import { SharedModule } from "../shared"

//
import {
  FooterComponent,
  NavigationComponent,
  NotificationComponent
} from "./components"

@NgModule({
  declarations: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    FormsModule,
    NgbCollapseModule,
    TranslateModule.forChild(),
    SharedModule,
    NgbAlertModule
  ],
  exports: [
    FooterComponent,
    NavigationComponent,
    NotificationComponent
  ]
})
export class CoreModule { }