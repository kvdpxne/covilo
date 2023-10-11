import {Component, Input} from "@angular/core";
import {Link, StorageKey, StorageService} from "../../../shared";

@Component({
  selector: "a-navigation",
  templateUrl: "./navigation.component.html",
})
export class NavigationComponent {

  // If true, will collapse the element or show it otherwise
  isCollapsed: boolean = true;

  private readonly storageService: StorageService;

  isLoggedIn: boolean;

  @Input()
  itemList!: Set<Link>;

  constructor(storageService: StorageService) {
    this.storageService = storageService;
    this.isLoggedIn = this.storageService.has(StorageKey.TOKEN);
  }

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed;
  };
}
