import {ChangeDetectorRef, Component} from "@angular/core";
import {
  MatSidenav,
  MatSidenavContainer,
  MatSidenavContent, MatSidenavModule
} from "@angular/material/sidenav";
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatListItem, MatListModule, MatNavList} from "@angular/material/list";
import {MatButtonModule, MatIconButton} from "@angular/material/button";
import {
  SideBarNavigationComponent
} from "../side-bar-navigation/side-bar-navigation.component";
import {MediaMatcher} from "@angular/cdk/layout";
import {MatIconModule} from "@angular/material/icon";
import {RouterLink} from "@angular/router";
import {HeaderBarComponent} from "../header-bar/header-bar.component";

@Component({
  selector: 'app-side-bar',
  standalone: true,
  imports: [
    MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, RouterLink, HeaderBarComponent
  ],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.scss'
})
export class SideBarComponent {

  mobileQuery: MediaQueryList;

  items = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);



  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
}
