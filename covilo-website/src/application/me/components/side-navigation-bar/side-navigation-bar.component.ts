import {Component, Input} from "@angular/core";
import {SideNavigationListComponent} from "../side-navigation-list/side-navigation-list.component";
import {AvatarImageComponent} from "../../../shared";

@Component({
  selector: "our-me-side-navigation-bar",
  templateUrl: "./side-navigation-bar.component.html",
  standalone: true,
  imports: [
    SideNavigationListComponent,
    AvatarImageComponent
  ]
})
export class SideNavigationBarComponent {

  @Input()
  public fullName?: string;

  @Input()
  public email?: string;
}
