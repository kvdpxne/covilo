import {ClassProvider} from "@angular/core";
import {TitleStrategy} from "@angular/router";
import {PageTitleStrategy} from "./page-title-strategy";

/**
 * A provider definition for the custom page title strategy.
 *
 * This provider specifies that the {@link PageTitleStrategy} class should
 * be used as the implementation for the {@link TitleStrategy} token.
 */
export const PAGE_TITLE_STRATEGY_PROVIDER: ClassProvider = {
  useClass: PageTitleStrategy,
  provide: TitleStrategy
};