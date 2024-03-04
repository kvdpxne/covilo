import {ApplicationConfig, importProvidersFrom} from "@angular/core";
import {
  provideRouter
} from "@angular/router";

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {AuthenticationModule} from "./authentication/authentication.module";
import {PAGE_TITLE_STRATEGY_PROVIDER} from "./shared";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),

    PAGE_TITLE_STRATEGY_PROVIDER,

    importProvidersFrom(
      AuthenticationModule
    )
  ]
};
