import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: "route-statistics",
  templateUrl: "./statistics.component.html",
  styleUrls: [
    "./statistics.component.scss"
  ]
})
export class StatisticsComponent {

  public constructor(router: Router) {
    router.navigateByUrl("/coming-soon");
  }
}
