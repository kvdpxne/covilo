import {Component} from "@angular/core";
import {NgFor} from "@angular/common";
import {TranslateModule} from "@ngx-translate/core";
import {SideNavigationItemComponent} from "../side-navigation-item/side-navigation-item.component";

@Component({
  selector: "our-side-navigation",
  templateUrl: "./side-navigation.component.html",
  standalone: true,
  imports: [
    NgFor,
    TranslateModule,
    SideNavigationItemComponent
  ]
})
export class SideNavigationComponent {

  public readonly links = [{
    reference: "/me/overview",
    translatableNameKey: "overview",
    iconName: "person-vcard"
  }, {
    reference: "/me/appearance",
    translatableNameKey: "appearance",
    iconName: "brush"
  }, {
    reference: "/me/contact",
    translatableNameKey: "contact",
    iconName: "envelope-at"
  }, {
    reference: "/me/security",
    translatableNameKey: "security",
    iconName: "shield-lock"
  }];
}
