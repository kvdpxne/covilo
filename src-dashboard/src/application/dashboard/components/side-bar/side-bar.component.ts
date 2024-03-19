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

@Component({
  selector: "app-side-bar",
  standalone: true,
  imports: [
    MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, RouterLink, HeaderBarComponent, TopBarComponent
  ],
  templateUrl: "./side-bar.component.html",
  styleUrl: "./side-bar.component.scss"
})
export class SideBarComponent {

  mobileQuery: MediaQueryList;

  items = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);


  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia("(max-width: 600px)");
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
}
