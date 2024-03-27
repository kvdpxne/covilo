import {ChangeDetectorRef, Component} from "@angular/core";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {MatButtonModule} from "@angular/material/button";
import {MediaMatcher} from "@angular/cdk/layout";
import {MatIconModule} from "@angular/material/icon";
import {RouterLink} from "@angular/router";
import {HeaderBarComponent} from "../header-bar/header-bar.component";
import {TopBarComponent} from "../top-bar/top-bar.component";
import {TitleCasePipe} from "@angular/common";
import {
  SideBarNavigationItemComponent
} from "../side-bar-navigation-item/side-bar-navigation-item.component";

@Component({
  selector: "app-side-bar",
  standalone: true,
  imports: [
    MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, RouterLink, HeaderBarComponent, TopBarComponent, TitleCasePipe, SideBarNavigationItemComponent
  ],
  templateUrl: "./side-bar.component.html",
  styleUrl: "./side-bar.component.scss"
})
export class SideBarComponent {

  mobileQuery: MediaQueryList;

  items = [{
    name: "Dashboard",
    href: "/dashboard/welcome",
    icon: "space_dashboard"
  }, {
    name: "User Management",
    href: "/dashboard/user-management",
    icon: "manage_accounts"
  }, {
    name: "View Reports",
    href: "",
    icon: "report"
  }, {
    name: "Incident Management",
    href: "",
    icon: ""
  }, {
    name: "Statistics & Reporting",
    href: "",
    icon: "bar_chart"
  }, {
    name: "System Settings",
    href: "",
    icon: "room_preferences"
  }, {
    name: "Notifications Handling",
    href: "",
    icon: "notifications_active"
  }, {
    name: "Comment Management",
    href: "",
    icon: "forum"
  }, {
    name: "Action Audit",
    href: "",
    icon: ""
  }, {
    name: "Support",
    href: "",
    icon: "help"
  }]


  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia("(max-width: 600px)");
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
}
