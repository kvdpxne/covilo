import {Component, Input} from "@angular/core";
import {MatListItem} from "@angular/material/list";
import {RouterLink} from "@angular/router";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: "app-side-bar-navigation-item",
  standalone: true,
  imports: [
    MatListItem,
    RouterLink,
    MatIcon
  ],
  templateUrl: "./side-bar-navigation-item.component.html",
  styleUrl: "./side-bar-navigation-item.component.scss"
})
export class SideBarNavigationItemComponent {

  @Input()
  public item: any;
}
