import {Component} from "@angular/core";
import {NgFor} from "@angular/common";
import {TranslateModule} from "@ngx-translate/core";
import {SideNavigationListItemComponent} from "../side-navigation-list-item/side-navigation-list-item.component";

@Component({
  selector: "our-me-side-navigation-list",
  templateUrl: "./side-navigation-list.component.html",
  standalone: true,
  imports: [
    NgFor,
    TranslateModule,
    SideNavigationListItemComponent
  ]
})
export class SideNavigationListComponent {

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
