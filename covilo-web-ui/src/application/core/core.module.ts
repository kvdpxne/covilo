import { NgModule } from "@angular/core"
import { FormsModule } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { BrowserModule } from "@angular/platform-browser"
import { NgbCollapseModule } from "@ng-bootstrap/ng-bootstrap"
import { TranslateModule } from "@ngx-translate/core"
import { FooterComponent, HeaderComponent } from "./components"
import { SharedModule } from "../shared/shared.module"

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    FormsModule,
    NgbCollapseModule,
    TranslateModule.forChild(),
    SharedModule
  ],
  exports: [
    FooterComponent,
    HeaderComponent
  ]
})
export class CoreModule {
}

export {
  Comment,
  CommentReaction,
  Crime,
  CrimeClassification,
  CrimePerpetrator,
  LocationCity,
  LocationCountry,
  LocationRegion,
  User,
  CrimeService,
  CrimeClassificationService,
  LocationCityService,
  LocationCountryService,
  LocationRegionService,
  UserService
} from "./index"