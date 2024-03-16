import { Component } from '@angular/core';
import {
  HeaderBarTitleComponent
} from "../header-bar-title/header-bar-title.component";
import {
  HeaderBarBreadcrumbsComponent
} from "../header-bar-breadcrumbs/header-bar-breadcrumbs.component";

@Component({
  selector: 'app-header-bar',
  standalone: true,
  imports: [
    HeaderBarTitleComponent,
    HeaderBarBreadcrumbsComponent
  ],
  templateUrl: './header-bar.component.html'
})
export class HeaderBarComponent {

}
