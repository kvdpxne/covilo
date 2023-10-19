import {Injectable} from "@angular/core";
import {Location} from "@angular/common";
import {Event, NavigationEnd, NavigationExtras, Router} from "@angular/router";

@Injectable({
  providedIn: "root"
})
export class NavigationService {

  private readonly router: Router;
  private readonly location: Location;

  private history: string[];

  public constructor(router: Router, location: Location) {
    this.router = router;
    this.location = location;

    //
    this.history = [];

    //
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.history.push(event.urlAfterRedirects);
      }
    });
  }

  public navigateTo(
    commands: any | any[],
    extras?: NavigationExtras
  ): void {
    // Checks whether the passed parameter is an array, if not, it will be
    // added to the newly created 1-element array.
    if (!Array.isArray(commands)) {
      commands = [commands];
    }

    this.router.navigate(commands, extras).catch((reason: any): void => {
      console.error(reason);
    });
  }

  public navigateToHomePage(
    extras?: NavigationExtras
  ): void {
    this.navigateTo("/", extras);
  }

  public forward(): void {
    this.location.forward();
  }

  /**
   * Navigates back in the platform's history or the site home page when
   * history
   */
  public back(): void {
    this.history.pop();
    if (0 >= this.history.length) {
      this.navigateToHomePage();
      return;
    }
    this.location.back();
  }
}
