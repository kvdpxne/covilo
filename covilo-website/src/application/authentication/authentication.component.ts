import {Component} from "@angular/core";
import {NavigationStart, Router} from "@angular/router";

@Component({
  selector: "covilo-authentication",
  templateUrl: "./authentication.component.html",
  styleUrls: [
    "./authentication.component.scss"
  ]
})
export class AuthenticationComponent {

  public childPath?: string;

  public constructor(router: Router) {
    const search: RegExp = /\//g;
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.childPath = event.url.replace(search, ".");
      }
    });
    if (!this.childPath) {
      this.childPath = router.url.replace(search, ".");
    }
  }
}
