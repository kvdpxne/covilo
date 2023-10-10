import {Component, Input} from "@angular/core";
import {Link} from "../../../shared";
import {environment} from "../../../../environments/environment";
import {User} from "../../../core";

@Component({
  selector: "side-navigation-bar",
  templateUrl: "./side-navigation-bar.component.html",
  styleUrls: [
    "./side-navigation-bar.component.scss"
  ]
})
export class SideNavigationBarComponent {

  @Input()
  public user?: User

  public avatar: string = `${environment.resourceUrl}`

  @Input()
  public fullName?: string;

  @Input()
  public email?: string;

  public fs: Link[] = [{
    name: "Overview",
    reference: "./me/overview"
  }, {
    name: "Change avatar",
    reference: "./me/change-avatar"
  }, {
    name: "Change password",
    reference: "./me/change-password"
  }];
}
