import {Component} from "@angular/core";
import {NavigationStart, Router, RouterOutlet} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {NavigationBarComponent, SideBarComponent} from "./components";

@Component({
  selector: "covilo-authentication",
  templateUrl: "./authentication.component.html",
  standalone: true,
  imports: [NavigationBarComponent, SideBarComponent, RouterOutlet, TranslateModule]
})
export class AuthenticationComponent {

  public childPath?: string;

  public constructor(router: Router) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.childPath = this.normalize(event.url);
      }
    });
    if (!this.childPath) {
      this.childPath = this.normalize(router.url);
    }
  }

  private normalize(url: string): string {
    url = url.replace(/\//g, ".").substring(1);

    const index = url.indexOf("-");
    url = url.replace(/-/g, "");

    return url.substring(0, index)
      .concat(url.charAt(index).toUpperCase())
      .concat(url.substring(index + 1));
  }
}
