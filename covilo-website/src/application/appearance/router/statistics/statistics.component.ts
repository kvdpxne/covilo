import {Component} from "@angular/core";
import {NavigationService} from "../../../shared";

@Component({
  selector: "router-statistics",
  templateUrl: "./statistics.component.html"
})
export class StatisticsComponent {

  public constructor(navigationService: NavigationService) {
    navigationService.navigateTo("/coming-soon");
  }
}
