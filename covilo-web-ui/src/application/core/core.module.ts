import { NgModule } from "@angular/core"
import { FormsModule } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { BrowserModule } from "@angular/platform-browser"
import { NgbAlertModule, NgbCollapseModule } from "@ng-bootstrap/ng-bootstrap"
import { TranslateModule } from "@ngx-translate/core"


//
import { SharedModule } from "../shared/shared.module"

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
export class CoreModule {
}

// All available application models
export * from "./models/capital-type"
export * from "./models/comment"
export * from "./models/comment-reaction"
export * from "./models/crime"
export * from "./models/crime-classification"
export * from "./models/crime-perpetrator"
export * from "./models/location-city"
export * from "./models/location-country"
export * from "./models/location-region"
export * from "./models/user"
export * from "./models/user-profile"

// All available application services
export * from "./services/crime.service"
export * from "./services/crime-classification.service"
export * from "./services/location-city.service"
export * from "./services/location-country.service"
export * from "./services/location-region.service"
export * from "./services/user.service"