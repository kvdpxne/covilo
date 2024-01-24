import {Component, Input} from "@angular/core";
import {SideNavigationComponent} from "../side-navigation/side-navigation.component";
import {AvatarImageComponent} from "../../../shared";

@Component({
  selector: "our-side-navigation-bar",
  templateUrl: "./side-navigation-bar.component.html",
  standalone: true,
  imports: [
    SideNavigationComponent,
    AvatarImageComponent
  ]
})
export class SideNavigationBarComponent {

  @Input()
  public fullName?: string;

  @Input()
  public email?: string;
}
