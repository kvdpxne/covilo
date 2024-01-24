import {Component} from "@angular/core";

@Component({
  selector: "side-navigation",
  templateUrl: "./side-navigation.component.html"
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
