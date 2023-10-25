import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: "router-statistics",
  templateUrl: "./statistics.component.html"
})
export class StatisticsComponent {

  public constructor(router: Router) {
    router.navigate(["/coming-soon"]).catch(error => console.error(error));
  }
}
