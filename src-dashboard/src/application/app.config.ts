import {ApplicationConfig, importProvidersFrom} from "@angular/core";
import {provideRouter} from "@angular/router";

import {routes} from "./app.routes";
import {
  provideAnimationsAsync
} from "@angular/platform-browser/animations/async";
import {
  AuthenticationModule
} from "./authentication";
import {PAGE_TITLE_STRATEGY_PROVIDER} from "./shared";
import {DashboardModule} from "./dashboard/dashboard.module";
import {HttpClientModule, provideHttpClient} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(),

    PAGE_TITLE_STRATEGY_PROVIDER,

    importProvidersFrom(
      HttpClientModule,

      AuthenticationModule,
      DashboardModule
    ),


  ]
};
