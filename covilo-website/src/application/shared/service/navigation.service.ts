import {Injectable} from "@angular/core";
import {Location} from "@angular/common";
import {Event, NavigationEnd, Router} from "@angular/router";
import {throwError} from "rxjs";

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

  /**
   * Navigates back in the platform's history or the site home page when
   * history
   */
  back(): void {
    this.history.pop();
    if (0 >= this.history.length) {
      this.router.navigateByUrl("/").catch(error => throwError(() => error));
      return;
    }
    this.location.back();
  }
}
