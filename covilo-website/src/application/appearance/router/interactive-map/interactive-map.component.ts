import {Component} from "@angular/core";
import {NavigationService} from "../../../shared";

@Component({
  selector: "router-interactive-map",
  templateUrl: "./interactive-map.component.html",
  standalone: true
})
export class InteractiveMapComponent {

  public constructor(navigationService: NavigationService) {
    navigationService.navigateTo("/coming-soon");
  }
}
