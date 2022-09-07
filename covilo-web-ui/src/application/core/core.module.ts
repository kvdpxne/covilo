import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserModule } from "@angular/platform-browser";
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { FooterComponent, HeaderComponent, } from './components';
import { SharedModule } from "../shared/shared.module";

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    FormsModule,
    NgbCollapseModule,
    TranslateModule.forChild(),
    SharedModule,
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
  ],
})
export class CoreModule {
}

// Models
export { Crime } from "./models/crime";
export { CrimeClassification } from "./models/crime-classification";
export { LocationCity } from "./models/location-city";
export { LocationCountry } from "./models/location-country";
export { LocationRegion } from "./models/location-region";

// Services
export { CrimeService } from "./services/crime.service"
export {
  CrimeClassificationService
} from "./services/crime-classification.service"
export { LocationCityService } from "./services/location-city.service"
export { LocationRegionService } from "./services/location-region.service"
export { LocationCountryService } from "./services/location-country.service"