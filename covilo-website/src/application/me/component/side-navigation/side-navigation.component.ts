import {Component, Input} from "@angular/core";
import {Link} from "../../../shared";

@Component({
  selector: "side-navigation",
  templateUrl: "./side-navigation.component.html"
})
export class SideNavigationComponent {

  @Input()
  content?: Link[];
}
