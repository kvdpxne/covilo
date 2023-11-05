import {Component} from "@angular/core";
import {Link} from "../../../shared";
import {ROUTE_LINKS} from "../../me-routing.module";

@Component({
  selector: "side-navigation",
  templateUrl: "./side-navigation.component.html"
})
export class SideNavigationComponent {

  public readonly links: Link[] = ROUTE_LINKS;
}
