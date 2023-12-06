import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";

import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

//
import {CoreModule} from "./core";

//
import {SharedModule} from "./shared";

//
import {AuthenticationModule} from "./authentication";

import {ApplicationRoutingModule} from "./application-routing.module";

import {ApplicationComponent} from "./application.component";

import {AppearanceModule} from "./appearance";
import {MeModule} from "./me";
import {LineChartModule} from "@swimlane/ngx-charts";
import {ComingSoonComponent, PageNotFoundComponent} from "./communication";

@NgModule({
  declarations: [
    ApplicationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),

    /*
     *
     * IMPORTANT: Imports in the wrong order may cause the application to work
     * incorrectly or cause other undesirable errors.
     */
    SharedModule.forRoot(),
    CoreModule,

    AppearanceModule,
    AuthenticationModule,
    MeModule,
    ApplicationRoutingModule,

    NgbModule,
    LineChartModule
  ],
  exports: [
    ApplicationComponent
  ],
  bootstrap: [
    ApplicationComponent
  ]
})
export class ApplicationModule {
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, "assets/localization/", ".json");
}
