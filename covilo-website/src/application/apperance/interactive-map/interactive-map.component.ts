import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: "router-interactive-map",
  templateUrl: "./interactive-map.component.html"
})
export class InteractiveMapComponent {

  public constructor(router: Router) {
    router.navigate(["/coming-soon"]).catch(error => console.error(error));
  }
}
