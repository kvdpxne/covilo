import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";

import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

import {NgbModule} from "@ng-bootstrap/ng-bootstrap";


//
import {AuthenticationModule} from "./authentication";

import {ApplicationRoutingModule} from "./application-routing.module";

import {ApplicationComponent} from "./application.component";

import {FooterComponent, NavigationComponent} from "./core";
import {MeRoutingModule} from "./me/me-routing.module";

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

    AuthenticationModule,

    MeRoutingModule,
    ApplicationRoutingModule,
    NavigationComponent,
    FooterComponent
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
