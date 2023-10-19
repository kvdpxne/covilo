import {Component, Input} from "@angular/core";
import {Link} from "../../../shared";

@Component({
  selector: "side-navigation-bar",
  templateUrl: "./side-navigation-bar.component.html"
})
export class SideNavigationBarComponent {

  @Input()
  public fullName?: string;

  @Input()
  public email?: string;

  public fs: Link[] = [{
    name: "Overview",
    reference: "/me/overview"
  }, {
    name: "Change avatar",
    reference: "/me/change-avatar"
  }, {
    name: "Change password",
    reference: "/me/change-password"
  }];
}
