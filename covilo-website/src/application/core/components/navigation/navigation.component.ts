import {Component, Input, OnInit} from "@angular/core";
import {Link} from "../../../shared/types/link";
import {StorageService} from "../../../shared/services/storage.service";
import {StorageKey} from "../../../shared/services/storage-key";

@Component({
  selector: "a-navigation",
  templateUrl: "./navigation.component.html",
})
export class NavigationComponent implements OnInit {

  // If true, will collapse the element or show it otherwise
  isCollapsed: boolean = true

  private readonly storageService: StorageService;

  isLoggedIn?: boolean;

  @Input()
  itemList!: Set<Link>

  constructor(storageService: StorageService) {
    this.storageService = storageService;
    this.isLoggedIn = false;
  }

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed
  };

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.has(StorageKey.TOKEN);
  }
}
